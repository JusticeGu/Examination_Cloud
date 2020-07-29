package com.exam.dao;


import com.exam.entity.Course;
import com.exam.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface QuestionsDAO  extends JpaRepository<Questions,Integer> {
    Questions findByQid(int qid);
    List<Questions> findAllByCourse(Course course);
    List<Questions> findAllByType(int type);
   // List<Questions> findAllByCidAndTypeAndDiffcult(int cid,int type,int diffcult);
  //  List<Questions> findAllByCourseIDAndDiffcult(int cid,int diffcult);
    List<Questions> findAllByTypeAndCourse(int type,Course course);
    @Query(nativeQuery =true,value = "select * from questions limit 10")
    List<Questions> getLastTen();

}
