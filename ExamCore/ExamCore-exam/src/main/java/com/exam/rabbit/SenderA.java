package com.exam.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SenderA {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(Map map) {
        System.out.println("答案正在上传至队列");
        //使用AmqpTemplate将消息发送到消息队列QueueHello中去
        this.rabbitTemplate.convertAndSend("paper_exchange_cloud","paper_queue_cloud",map);
    }
    public boolean sendpaper(int kid, int pid, String uno,Map ansmap){
        Map map = new HashMap();
        map.put("kid",kid);
        map.put("pid",pid);
        map.put("uno",uno);
        map.put("ansmap",ansmap);
        send(map);
        return true;
    }
}