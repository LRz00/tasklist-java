/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.LRz00.tasklist.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author lara
 */
@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    public String generateToken(String username){
        SecretKey key = getKeyBySecret();
        
        return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + this.expiration)).
                signWith(key).compact();
    }
    
    private SecretKey getKeyBySecret(){
        SecretKey key = Keys.hmacShaKeyFor(this.secret.getBytes());
        
        return key;
    }
    
    public String getUsername(String token){
        Claims claims = getClaims(token);
        
        if(Objects.nonNull(claims)){
            return claims.getSubject();
        }
        return null;
    }
    
    public boolean isValidTOken(String token){
        Claims claims = getClaims(token);
        
        if(Objects.nonNull(claims)){
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if(Objects.nonNull(username) && Objects.nonNull(expirationDate) && now.before(expirationDate)){
                return true;
            }
        }
        return false;
      
    }
    
    
    private Claims getClaims(String token){
        SecretKey key = getKeyBySecret();
        
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e){
            return null;
        }
    }
}
