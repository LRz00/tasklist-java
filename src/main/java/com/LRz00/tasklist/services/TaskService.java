/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.LRz00.tasklist.services;

import com.LRz00.tasklist.models.Task;
import com.LRz00.tasklist.models.User;
import com.LRz00.tasklist.models.enums.ProfileEnum;
import com.LRz00.tasklist.models.projection.TaskProjection;
import com.LRz00.tasklist.repositories.TaskRepository;
import com.LRz00.tasklist.security.UserSpringSecurity;
import com.LRz00.tasklist.services.exceptions.AuthorizationException;
import com.LRz00.tasklist.services.exceptions.DataBindingException;
import com.LRz00.tasklist.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lara
 */
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepo;
    
    @Autowired
    private UserService userService;
    
    public Task findById(Long id){
        Task task = this.taskRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Tarefa não encontrada"));
        
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && userHasTask(userSpringSecurity, task)){
            throw new AuthorizationException("Acesso negado");
        }
        
        return task;
    }
    @Transactional
    public Task create(Task task){
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
         
         if(Objects.isNull(userSpringSecurity)){
             throw new AuthorizationException("Acesso negado!");
         }
        
        
        User user = this.userService.findById(userSpringSecurity.getId());
        task.setId(null);
        task.setUser(user);
        task = this.taskRepo.save(task);
        return task;
    }
    
    @Transactional
    public Task update(Task task){
        Task newTask = findById(task.getId());
        newTask.setDescription((task.getDescription()));
        return this.taskRepo.save(newTask);
    }
    
    public void delete(Long id){
        findById(id);
        
        try{
            this.taskRepo.deleteById(id);
        }catch(Exception e){
            throw new DataBindingException("Não é possivel deletar");
        }
        
    }
    
    public List<TaskProjection> findAllByUser(){
         UserSpringSecurity userSpringSecurity = UserService.authenticated();
         
         if(Objects.isNull(userSpringSecurity)){
             throw new AuthorizationException("Acesso negado!");
         }
         
         List<TaskProjection> tasks = this.taskRepo.findByUser_Id(userSpringSecurity.getId());
         return tasks;
         
    }
    
    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task){
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }
}
