
package com.exam.rabbit;


import com.exam.service.ExamDataService;
import com.exam.service.PaperService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author longzhonghua
 * @data 2019/02/03 11:07
 */

@Component
@RabbitListener(queues ="paper_queue_cloud")//监听QueueHello的消息队列
public class PaperReceiver {
    @Autowired
    PaperService paperService;
    @Autowired
    ExamDataService examDataService;
    @RabbitHandler//@RabbitHandler来实现具体消费
    public void QueueReceiver(Map ansmap) {
        int kid = (int)ansmap.get("kid");
        int pid = (int)ansmap.get("pid");
        String uno = (String) ansmap.get("uno");
        Map ansmp = (Map)ansmap.get("ansmap");
        try {
            Map score = paperService.markscore(pid,ansmp);
            examDataService.updatescore(kid, pid,uno, ansmp, (float)score.get("score"),score.get("wrong").toString());
            System.out.println("Mail：已接收到来自队列正在处理...");
        }catch (Exception e){
            System.out.println("wrong"+e);
        }


    }

}
