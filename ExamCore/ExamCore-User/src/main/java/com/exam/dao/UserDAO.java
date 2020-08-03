package com.exam.dao;


import com.exam.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    User findById(int uid);
    User findByUno(String uno);

    User findByWxuid(String wxuid);

    @Query(nativeQuery = true, value = "select name from user where uno = ?1")
    String findNameByUno(String uno);

    @Query(nativeQuery = true, value = "select uno from user where username = ?1")
    String findUnoByUsername(String username);

    void deleteByUId(int uid);
//   User getByUsernameAndPassword(String username);
}