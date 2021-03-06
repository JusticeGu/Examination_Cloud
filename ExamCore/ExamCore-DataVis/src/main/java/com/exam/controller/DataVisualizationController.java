package com.exam.controller;



import com.exam.result.ExceptionMsg;
import com.exam.result.ResponseData;
import com.exam.service.DataVisualizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JunXxxxi
 * @date 2020/7/20 18:10
 **/

@RestController
@Api(tags = "数据可视化接口")
@RequestMapping("/api/datavisualization")
public class DataVisualizationController {
    @Autowired
    DataVisualizationService dataVisualizationService;

    @GetMapping("/numOfExam")
    @ApiOperation("老师端近7天发布考试数量情况统计")
    public ResponseData getNumOfExam(){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.getNumOfExam());
    }

    @GetMapping("/generalDataOfTeacher")
    @ApiOperation("老师端单次考试数据概况")
    public ResponseData getNumOfStudents(@RequestParam int kid){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.getNumOfStudents(kid));
    }

    @GetMapping("/disOfScore")
    @ApiOperation("老师端单次考试分数分布")
    public ResponseData getDisOfScore(@RequestParam int kid){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.getDisOfScore(kid));
    }

    @GetMapping("/wrongSituation")
    @ApiOperation("错误最多的题目统计")
    public ResponseData getWrongSituation(@RequestParam int kid){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.getWrongSituation(kid));
    }
    @GetMapping("/wrongtop")
    @ApiOperation("当日错题统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "排行榜范围", value = "range", defaultValue = "10", required = true)
    })
    public ResponseData getwrongtop(@RequestParam int range){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.wrongtop(range));
    }
    @GetMapping("/riaghtRate")
    @ApiOperation("老师端题目正确率统计")
    public ResponseData getRiaghtRate(@RequestParam int kid){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.getRiaghtRate(kid));
    }

    @GetMapping("/studentsList")
    @ApiOperation("单次考试所有参加的学生列表（包括姓名、学号、成绩）")
    public ResponseData getStudentsList(@RequestParam int kid){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.getStudentsList(kid));
    }

    @GetMapping("/Tdashboard")
    @ApiOperation("老师端首页统计信息")
    public ResponseData getDashboard(){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.getDashboard());
    }

    @GetMapping("/Sdashboard")
    @ApiOperation("学生端首页统计信息")
    public ResponseData getSDashboard(){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.getSDashboard());
    }

    @GetMapping("/stuExamLast7D")
    @ApiOperation("学生端首页过去7天考试统计图数据")
    public ResponseData getNumOfSExam(){
        return new ResponseData(ExceptionMsg.SUCCESS, dataVisualizationService.getNumOfSExam());
    }



}
