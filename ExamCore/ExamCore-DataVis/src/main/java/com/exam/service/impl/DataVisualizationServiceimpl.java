package com.exam.service.impl;


import com.exam.dao.ExamdataDAO;
import com.exam.dao.ExroomDAO;
import com.exam.dao.PaperDAO;
import com.exam.dao.UserDAO;
import com.exam.service.DataVisualizationService;
import com.exam.service.QuestionsService;
import com.exam.service.RedisService;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author JunXxxxi
 * @date 2020/7/20 18:10
 **/

@Service
public class DataVisualizationServiceimpl implements DataVisualizationService {
    @Autowired
    ExroomDAO exroomDAO;
    @Autowired
    ExamdataDAO examedataDAO;
    @Autowired
    PaperDAO paperDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    RedisService redisService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionsService questionsService;

    @Override
    public List<Integer> getNumOfExam() {
        String name = userService.getusernamebysu();
        Date now= new Date();
        Long current = now.getTime();
        Long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        Long t = zero;
        List<Integer> numOfExam = new ArrayList<>();
        t = t - 6 * 24*60*60*1000;
        for (int i=0;i<7;i++){
            numOfExam.add(exroomDAO.getNumExamPerDay(t,name));
            t = t + 24*60*60*1000;
        }
        return numOfExam;
    }

    @Override
    public List<Integer> getNumOfStudents(int kid) {
        List<Integer> dataList = new ArrayList<Integer>();
        Integer a = examedataDAO.countByKid(kid);
        dataList.add(a);
        Integer b = examedataDAO.countPass(kid);
        dataList.add(b);
        dataList.add(a-b);
        dataList.add(examedataDAO.countExcellent(kid));
        int i = examedataDAO.sumOfScore(kid);
        int j = a;
        i = i/j;
        Integer c = i;
        dataList.add(c);
        dataList.add(examedataDAO.maxOfScore(kid));
        dataList.add(examedataDAO.minOfScore(kid));
        return dataList;
    }

    @Override
    public List<Integer> getDisOfScore(int kid) {
        List<Integer> dataList = new ArrayList<Integer>();
        dataList.add(examedataDAO.countExcellent(kid));
        dataList.add(examedataDAO.countGood(kid));
        dataList.add(examedataDAO.countBad(kid));
        dataList.add(examedataDAO.countPoor(kid));
        return dataList;
    }

    @Override
    public Map<Integer, Integer> getWrongSituation(int kid) {
        List<Integer> dataList = new ArrayList<Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int pid = exroomDAO.findPidByKid(kid);
        int n = paperDAO.queryNumOfQueByPid(pid);
        int m = 0, index = 0;
        for(int i=1;i<=n;i++){
            dataList.add(examedataDAO.getNumOfWrong(kid,i));
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<n;j++){
                if(dataList.get(j)>m){
                    m = dataList.get(j);
                    index = j;
                }
            }
            map.put(index+1,m);
            dataList.set(index, -1);
            m = -1;
        }
        return map;
    }

    @Override
    public Map<Integer,Double> getRiaghtRate(int kid) {
        Map<Integer, Double> map = new HashMap<Integer, Double>();
        int pid = examedataDAO.getPid(kid);
        int n = paperDAO.queryNumOfQueByPid(pid);
        int numOfStudents = examedataDAO.countByKid(kid);
        for(int i=1;i<=n;i++){
            double m = 1-examedataDAO.getNumOfWrong(kid,i)*1.0/(numOfStudents*1.0);
            Double mm = Double.valueOf(String.format("%.2f", m));
            map.put(i,mm);
        }
        return map;
    }

    @Override
    public Map getStudentsList(int kid) {
        Map<String, Object> studentsList = new HashMap();
        List<Float> scoreList;
        scoreList = examedataDAO.findTotalscoreByKid(kid);
        List<String> unoList;
        unoList = examedataDAO.findUnoByKid(kid);
        for(int i=0;i<scoreList.size();i++){
            studentsList.put("name",userDAO.findNameByUno(unoList.get(i)));
            studentsList.put("uno",unoList.get(i));
            studentsList.put("totalscore",scoreList.get(i));
        }
        return studentsList;
    }

    @Override
    public boolean addwronglist(int qid) {
        try{
            double score = redisService.sortSetZincrby("wronglisttop",Integer.toString(qid),1 );
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List wrongtop(int range) {
        if (redisService.get("Wrongtop")==null){
        Set qidset=redisService.sortSetRange("wronglisttop",0,range-1);
        List<String> questionsList = new ArrayList<>();
        for (Object qid:qidset){
            questionsList.add(questionsService.getquestionbyid(Integer.parseInt(qid.toString())).getQuestionName());
        }
        redisService.set("Wrongtop",questionsList,3600*27*7);
        return questionsList;}
        else {
            return (List)redisService.get("Wrongtop");
        }
    }

    @Override
    public Map getDashboard() {
        String name = userService.getusernamebysu();
        Date now= new Date();
        Long current = now.getTime();
        Long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        Long t = zero;
        Map<String, Object> dashboard = new HashMap();
        dashboard.put("发布考试",exroomDAO.getNumExamPerMonth(t,name));
        dashboard.put("未开始考试",exroomDAO.getNumNotStart(t,current,name));
        dashboard.put("已开始/已结束考试",exroomDAO.getNumExamPerMonth(t,name)-exroomDAO.getNumNotStart(t,current,name));
        dashboard.put("添加试卷",paperDAO.getNumPaperPerMonth(t,name));
        return dashboard;
    }

    @Override
    public Map getSDashboard() {
        String name = userService.getusernamebysu();
        String uno = redisService.hmget("TK:"+name).get("uno").toString();
        Map<String, Object> dashboard = new HashMap();
        dashboard.put("参加考试",examedataDAO.countByUno(uno));
        if(examedataDAO.countByUno(uno)==0){
            dashboard.put("平均分",0);
        }
        else {
            dashboard.put("平均分", examedataDAO.sumOfScoreByUno(uno) / examedataDAO.countByUno(uno));
        }
        if(examedataDAO.maxOfScoreByUno(uno)==null){
            dashboard.put("最高分",0);
        }
        else {
            dashboard.put("最高分",examedataDAO.maxOfScoreByUno(uno));
        }
        if (examedataDAO.minOfScoreByUno(uno)==null){
            dashboard.put("最低分",0);
        }
        else {
            dashboard.put("最低分",examedataDAO.minOfScoreByUno(uno));
        }
        return dashboard;
    }

    @Override
    public List<Integer> getNumOfSExam() {
        String name = userService.getusernamebysu();
        String uno = redisService.hmget("TK:"+name).get("uno").toString();
        Date now= new Date();
        Long current = now.getTime();
        Long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        Long t = zero;
        List<Integer> numOfExam = new ArrayList<>();
        t = t - 6 * 24*60*60*1000;
        for (int i=0;i<7;i++){
            numOfExam.add(examedataDAO.getNumSExamPerDay(t,uno));
            t = t + 24*60*60*1000;
        }
        return numOfExam;
    }


}