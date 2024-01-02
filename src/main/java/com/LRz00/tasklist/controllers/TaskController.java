/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.LRz00.tasklist.controllers;

import com.LRz00.tasklist.models.Task;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.LRz00.tasklist.services.TaskService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author lara
 */
@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    @Autowired
    TaskService taskService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id){
        Task task = this.taskService.findById(id);
        return ResponseEntity.ok(task);
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Task>> findAllByUser() {
    List<Task> tasks = this.taskService.findAllByUser();
    return ResponseEntity.ok().body(tasks);
}
    
    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task task){
        Task newTask = this.taskService.create(task);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newTask.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Task task, @PathVariable Long id){
        task.setId(id);
        this.taskService.update(task);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();        
    }
}
