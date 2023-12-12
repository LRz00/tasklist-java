/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.LRz00.tasklist.services;

import com.LRz00.tasklist.repositories.TaskRepository;
import com.LRz00.tasklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.LRz00.tasklist.models.User;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    
    public User findById(Long id){
        Optional<User> user = this.userRepo.findById(id);
        return user.orElseThrow(() -> new RuntimeException("USUARIO NÂO ENCONTRADO"));
    }
    
    @Transactional
    public User create(User usuario){
        usuario.setId(null);
        usuario = this.userRepo.save(usuario);
        
        return usuario;     
    }
    
    @Transactional
    public User update(User usuario){
        User newUser = findById(usuario.getId());
        newUser.setPassword(usuario.getPassword());
        return this.userRepo.save(newUser);
    }
    
    public void delete(Long id){
        findById(id);
        try{
            this.userRepo.deleteById(id);
        } catch(Exception e){
            throw new RuntimeException("Não é possivel excluir pois há tarefas relacionadas.");
        }
    }
}
