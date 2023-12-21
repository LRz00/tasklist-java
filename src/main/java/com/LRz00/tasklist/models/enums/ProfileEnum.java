/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.LRz00.tasklist.models.enums;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author lara
 */
@Getter
@AllArgsConstructor
public enum ProfileEnum {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");
    
    private Integer code;
    private String description;
    
    public static ProfileEnum toEnum(Integer code){
        if(Objects.isNull(code)){
            return null;
        }
        
        for (ProfileEnum x : ProfileEnum.values()){
            if(code.equals(x.getCode())){
                return x;
            }
        }
        
        throw new IllegalArgumentException("Invalide code:" + code);
    }
}
