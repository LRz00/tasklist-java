/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.LRz00.tasklist.models.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author lara
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
}
