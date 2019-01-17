/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.core;

import java.util.Random;

/**
 *
 * @author deneme
 */
public class PasswordGenerator {

    public static String passwordGenerator() 
    { 

        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        String Small_chars = "abcdefghijklmnopqrstuvwxyz"; 
        String numbers = "0123456789"; 
  
  
        String values = Capital_chars + Small_chars + numbers; 
        String password="";
        Random rndm_method = new Random(); 
  
        for (int i = 0; i < 10; i++) 
        { 
            // Use of charAt() method : to get character value 
            // Use of nextInt() as it is scanning the value as int 
            password += values.charAt(rndm_method.nextInt(values.length())); 
  
        }
        return password; 
    } 
}
