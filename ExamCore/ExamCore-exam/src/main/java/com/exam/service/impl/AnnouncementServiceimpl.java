package com.exam.service.impl;


import com.exam.dao.AnnouncementDAO;
import com.exam.entity.Announcement;
import com.exam.service.AnnouncementService;
import com.exam.util.UpdateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author JunXxxxi
 * @date 2020/7/19 19:29
 **/
@Service
public class AnnouncementServiceimpl implements AnnouncementService {
    @Autowired
    AnnouncementDAO announcementDAO;

    @Override
    public List<Announcement> announcementList() {

        return announcementDAO.findAll();
    }

    @Override
    public int addAnnouncement(Announcement announcement) {
        Date now= new Date();
        Long createtime = now.getTime();
        announcement.setCreateTime(createtime);
        announcement.setUpdateTime(createtime);
        try{
            announcementDAO.save(announcement);
            return 1;
        }catch (IllegalArgumentException e) {
            return 2;
        }
    }

    @Override
    public int delAnnouncement(int aid) {
        if(!isexist(aid))
            return -1;
        announcementDAO.deleteById(aid);
        return 1;
    }

    private boolean isexist(int aid) {
        Announcement announcement = announcementDAO.findByAid(aid);
        return null!=announcement;
    }

    @Override
    public int modifyAnnouncement(Announcement announcement) {
        Date now= new Date();
        Long updateTime = now.getTime();
        announcement.setUpdateTime(updateTime);
        int id = announcement.getAid();
        Announcement oldAnnouncement = announcementDAO.findByAid(id);
        if(!StringUtils.isEmpty(oldAnnouncement)){
            UpdateUtil.copyNullProperties(announcement,oldAnnouncement);
        }
        try{
            announcementDAO.save(announcement);
            return 1;
        }catch (IllegalArgumentException e) {
            return 2;
        }
    }
}
