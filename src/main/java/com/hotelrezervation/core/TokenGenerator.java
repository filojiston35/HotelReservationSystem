/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.core;

import java.util.Random;
import com.hotelrezervation.model.MailToken;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author deneme
 */
public class TokenGenerator {

    private String personnel_Mail;
    private MailToken token;
    private Transaction tx1;

    public TokenGenerator(String personnel_Mail) {
        this.personnel_Mail = personnel_Mail;

    }

    public String tokenCreator() {
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String password = "";
        Random rndm_method = new Random();

        for (int i = 0; i < 15; i++) {
            // Use of charAt() method : to get character value 
            // Use of nextInt() as it is scanning the value as int 
            password += Small_chars.charAt(rndm_method.nextInt(Small_chars.length()));

        }
        return password;
    }

    public String getPersonnel_Mail() {
        return personnel_Mail;
    }

    public void setPersonnel_Mail(String personnel_Mail) {
        this.personnel_Mail = personnel_Mail;
    }

    public String addToken() {
        try {
            String token_code = tokenCreator();
            token=new MailToken();
            token.setToken(token_code);
            token.setTokenMail(personnel_Mail);
            token.setTokenDate(new Date());

            Session session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            Integer id = (Integer) session.save(token);
            tx1.commit();
            session.close();
            return token_code;
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";

    }

}
