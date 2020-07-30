package com.exam.dao;

import com.exam.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementDAO extends JpaRepository<Announcement,Integer> {
    Announcement findByAid(int aid);
}
