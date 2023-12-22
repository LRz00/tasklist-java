/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.LRz00.tasklist.repositories;

import com.LRz00.tasklist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lara
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
   @Transactional(readOnly = true) 
   User findByUsername(String username);
}
