/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.mail;

import com.hotelrezervation.model.EventPersonnel;
import com.hotelrezervation.model.Personnel;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author deneme
 */
public class SendMail {

    private String mail;
    private String nameSurname;
    private String user_password;
    private Properties properties;

    private String system_mail;
    private String system_password;
    private Personnel senderPersonnel = null;

    public SendMail() {
        properties = new Properties();
    }

    public void mailSettings() {
        system_mail = "hotel.reservation.system99@gmail.com";
        system_password = "ZHUnyf9VwRSE6mZw";
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
    }

    public void newPersonnel(String mail, String nameSurname, String user_password) {
        mailSettings();
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(system_mail, system_password);
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("biseyler@bisey.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            message.setSubject("Hotel Rezervasyon Sistemi - Çalışan Kaydı");
            message.setText("Sayın " + nameSurname + ",kaydınız başarıyla yapılmıştır.\n\n"
                    + "Parolanız:" + user_password + "\n\n"
                    + "iyi günler dileriz." + "\n\n"
                    + "http://localhost:8080/hotel-rezervation-1.0/login.xhtml");
            Transport.send(message);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void forgotPassword(String token, String mail) {
        mailSettings();
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(system_mail, system_password);
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("biseyler@bisey.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            message.setSubject("Hotel Reservasyon Sistemi - Parolamı Unuttum");
            message.setText("Parolanızı aşağıdaki linkten değiştirebilirsiniz:\n\n" + "http://localhost:8080/hotel-rezervation-1.0/secured/passwordChange.xhtml?token=" + token);
            Transport.send(message);

        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void newEventMail(List<Personnel> personnelList, String eventMessage, String eventTitle) throws AddressException {
        senderPersonnel = personnelList.get(personnelList.size() - 1);
        if (personnelList.size() > 1) {
            InternetAddress[] adress = new InternetAddress[personnelList.size() - 1];
            mailSettings();
            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(system_mail, system_password);
                }
            });
            for (int i = 0; i < personnelList.size() - 1; i++) {
                adress[i] = new InternetAddress(personnelList.get(i).getPersonnelMail());
            }
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("biseyler@bisey.com"));
                message.setRecipients(Message.RecipientType.TO, adress);
                message.setSubject("Hotel Rezervasyon Sistemi - Yeni Event");
                message.setContent(senderPersonnel.getPersonnelName() + " " + senderPersonnel.getPersonnelSurname() + " yeni bir etkinlik oluşturdu" + "\n\n"
                        + eventTitle + "\n\n"
                        + eventMessage + "\n\n"
                        + "http://localhost:8080/hotel-rezervation-1.0/login.xhtml", "text/html;charset=UTF-8");
                Transport.send(message);

            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
