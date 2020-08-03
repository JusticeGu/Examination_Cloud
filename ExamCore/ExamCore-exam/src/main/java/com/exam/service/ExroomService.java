package com.exam.service;


import com.exam.entity.Exroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ExroomService {
    public List<Exroom> listExroom();
    public Page<Exroom> listexroombynum(Pageable pageable);
    public Page<Exroom> listexroostu(Pageable pageable);
    public int addExroom(Exroom exroom);
    public int modifyExroom(Exroom exroom);
    public int delExroom(int kid);
    public Exroom findExroom(int kid);
    public boolean isExist(int kid);
    public List<Map<String, Object>> getExroomList();
    public Map enterExroom(int kid);
    public List<String> stringToList(String strs);
    public boolean putpermission(String exid,String uno);
    public boolean checkpermission(String exid, String uno);
    public int startexrooom(int kid);
    public int endroom(int kid);
    public int uploadgrouplist(MultipartFile multipartFile);
    public List<Exroom> getNotStartList();
    public List<Exroom> getStartedList();
    public List<Exroom> getLastThreeExam();
    public List<Exroom> getSLastThreeExam();
}
