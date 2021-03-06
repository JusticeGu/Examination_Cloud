package com.exam.dao;

import com.exam.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface AnswerDAO extends JpaRepository<Answer,Integer> {
    @Query(nativeQuery =true,value = "select qid from s_answer where eid = ?1 order by qid asc")
    List<Integer> getQidByEid(int eid);
    @Query(nativeQuery =true,value = "select answer_content from s_answer where eid = ?1 order by qid asc")
    List<String> getAnsByEid(int eid);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery =true,value = "update s_answer set answer_content = ?3 where eid = ?1 and qid = ?2")
    int updateAnswer(int eid,int qid,String anscontent);
    @Query(nativeQuery =true,value = "select qid from s_answer where eid = ?1")
    List<Integer> getQidListByEid(int eid);
    @Query(nativeQuery =true,value = "select visual_qid from s_answer where qid = ?1 and eid = ?2")
    int getVisualQidByQidAndEid(int qid,int eid);
}
