package com.hotelrezervation.model;
// Generated Dec 26, 2018 6:38:01 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EventDetail generated by hbm2java
 */
public class EventDetail  implements java.io.Serializable {


     private Integer eventId;
     private String eventTitle;
     private String eventMessage;
     private Date eventCreateDate;
     private Set eventPersonnels = new HashSet(0);

    public EventDetail() {
    }

	
    public EventDetail(String eventTitle, String eventMessage) {
        this.eventTitle = eventTitle;
        this.eventMessage = eventMessage;
    }
    public EventDetail(String eventTitle, String eventMessage, boolean eventIsRead, Date eventCreateDate, Set eventPersonnels) {
       this.eventTitle = eventTitle;
       this.eventMessage = eventMessage;
       this.eventCreateDate = eventCreateDate;
       this.eventPersonnels = eventPersonnels;
    }
   
    public Integer getEventId() {
        return this.eventId;
    }
    
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    public String getEventTitle() {
        return this.eventTitle;
    }
    
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
    public String getEventMessage() {
        return this.eventMessage;
    }
    
    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public Date getEventCreateDate() {
        return this.eventCreateDate;
    }
    
    public void setEventCreateDate(Date eventCreateDate) {
        this.eventCreateDate = eventCreateDate;
    }
    public Set getEventPersonnels() {
        return this.eventPersonnels;
    }
    
    public void setEventPersonnels(Set eventPersonnels) {
        this.eventPersonnels = eventPersonnels;
    }




}


