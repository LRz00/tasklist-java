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
import com.LRz00.tasklist.models.enums.ProfileEnum;
import com.LRz00.tasklist.security.UserSpringSecurity;
import com.LRz00.tasklist.services.exceptions.AuthorizationException;
import com.LRz00.tasklist.services.exceptions.DataBindingException;
import com.LRz00.tasklist.services.exceptions.ObjectNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    
    @Autowired BCryptPasswordEncoder passwordEncoder;
    
    public User findById(Long id){
        UserSpringSecurity userSpringSecurity = authenticated();
        if (!Objects.nonNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !id.equals(userSpringSecurity.getId())){
            throw new AuthorizationException("Acesso negado!");
        }
        
        Optional<User> user = this.userRepo.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("USUARIO NÂO ENCONTRADO"));
    }
    
    @Transactional
    public User create(User user){
        user.setId(null);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        
        user = this.userRepo.save(user);
        
        return user;     
    }
    
    @Transactional
    public User update(User usuario){
        User newUser = findById(usuario.getId());
        newUser.setPassword(usuario.getPassword());
        newUser.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
        return this.userRepo.save(newUser);
    }
    
    public void delete(Long id){
        findById(id);
        try{
            this.userRepo.deleteById(id);
        } catch(Exception e){
            throw new DataBindingException("Não é possivel excluir pois há tarefas relacionadas.");
        }
    }
    
    public static UserSpringSecurity authenticated(){
        try{
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch(Exception e){
            return null;
        }
    }
}
