package com.hotelrezervation.model;
// Generated Dec 26, 2018 6:38:01 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Reminders generated by hbm2java
 */
public class Reminders  implements java.io.Serializable {


     private Integer reminderId;
     private String reminderTitle;
     private String reminderMessage;
     private Date reminderStartDate;
     private Date reminderEndDate;
     private Date reminderCreateDate;
     private Integer reminderPersonnel;

    public Reminders() {
    }

    public Reminders(String reminderTitle, String reminderMessage, Date reminderStartDate, Date reminderEndDate, Date reminderCreateDate, Integer reminderPersonnel) {
       this.reminderTitle = reminderTitle;
       this.reminderMessage = reminderMessage;
       this.reminderStartDate = reminderStartDate;
       this.reminderEndDate = reminderEndDate;
       this.reminderCreateDate = reminderCreateDate;
       this.reminderPersonnel = reminderPersonnel;
    }
   
    public Integer getReminderId() {
        return this.reminderId;
    }
    
    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }
    public String getReminderTitle() {
        return this.reminderTitle;
    }
    
    public void setReminderTitle(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }
    public String getReminderMessage() {
        return this.reminderMessage;
    }
    
    public void setReminderMessage(String reminderMessage) {
        this.reminderMessage = reminderMessage;
    }
    public Date getReminderStartDate() {
        return this.reminderStartDate;
    }
    
    public void setReminderStartDate(Date reminderStartDate) {
        this.reminderStartDate = reminderStartDate;
    }
    public Date getReminderEndDate() {
        return this.reminderEndDate;
    }
    
    public void setReminderEndDate(Date reminderEndDate) {
        this.reminderEndDate = reminderEndDate;
    }
    public Date getReminderCreateDate() {
        return this.reminderCreateDate;
    }
    
    public void setReminderCreateDate(Date reminderCreateDate) {
        this.reminderCreateDate = reminderCreateDate;
    }
    public Integer getReminderPersonnel() {
        return this.reminderPersonnel;
    }
    
    public void setReminderPersonnel(Integer reminderPersonnel) {
        this.reminderPersonnel = reminderPersonnel;
    }




}


