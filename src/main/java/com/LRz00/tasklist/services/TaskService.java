/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.LRz00.tasklist.services;

import com.LRz00.tasklist.models.Task;
import com.LRz00.tasklist.models.User;
import com.LRz00.tasklist.repositories.TaskRepository;
import java.util.Optional;
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
        Optional<Task> task = this.taskRepo.findById(id);
        
        return task.orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }
    @Transactional
    public Task create(Task task){
        User user = this.userService.findById(task.getUser().getId());
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
            throw new RuntimeException("Não é possivel deletar");
        }
        
    }
}
