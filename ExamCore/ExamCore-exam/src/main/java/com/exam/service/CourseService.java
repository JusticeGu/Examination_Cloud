package com.exam.service;


import com.exam.entity.Course;

import java.util.List;

public interface CourseService {
    public List<Course> list();
    public int addCourse(Course course);
    public boolean isexist(int cid);
    public int delCourse(int cid);
    public List<Course> querycourse(String coursename);
    public Course findcourse(int cid);

}
