package com.exam.dao;



import com.exam.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDAO extends JpaRepository<Course,Integer> {
    Course findByCid(int cid);
    List<Course> findAllByCourseNameLike(String coursename);
}
