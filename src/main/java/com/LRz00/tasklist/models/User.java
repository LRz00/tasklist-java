/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.LRz00.tasklist.models;

import com.LRz00.tasklist.models.enums.ProfileEnum;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author lara
 */
@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;
    
    @OneToMany(mappedBy = "user")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<Task>();
    
    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name="profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles(){
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }
    
    public void addProfile(ProfileEnum profileEnum){
        this.profiles.add(profileEnum.getCode());
    }
}
