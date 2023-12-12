/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.LRz00.tasklist.repositories;

import com.LRz00.tasklist.models.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lara
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUser_Id(Long id);    
    
    //@Query(value = " SELECT t FROM Task t where t.user.id = :id")
    //List<Task> findByUserId(@Param("id") Long id); 
    
    //@Query(value = " SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    //List<Task> findByUserId(@Param("id") Long id);
}
