package com.hotelrezervation.model;
// Generated Dec 26, 2018 6:38:01 PM by Hibernate Tools 4.3.1

/**
 * EventPersonnel generated by hbm2java
 */
public class EventPersonnel implements java.io.Serializable {

    private int eventPersonnelId;
    private EventDetail eventDetail;
    private Personnel personnelByEventSenderId;
    private Personnel personnelByPersonnelId;
    private boolean eventIsRead;

    public EventPersonnel() {
    }

    public EventPersonnel(int eventPersonnelId, EventDetail eventDetail, Personnel personnelByEventSenderId, Personnel personnelByPersonnelId, boolean eventIsRead) {
        this.eventPersonnelId = eventPersonnelId;
        this.eventDetail = eventDetail;
        this.personnelByEventSenderId = personnelByEventSenderId;
        this.personnelByPersonnelId = personnelByPersonnelId;
        this.eventIsRead = eventIsRead;
    }

    public int getEventPersonnelId() {
        return this.eventPersonnelId;
    }

    public void setEventPersonnelId(int eventPersonnelId) {
        this.eventPersonnelId = eventPersonnelId;
    }

    public EventDetail getEventDetail() {
        return this.eventDetail;
    }

    public void setEventDetail(EventDetail eventDetail) {
        this.eventDetail = eventDetail;
    }

    public Personnel getPersonnelByEventSenderId() {
        return this.personnelByEventSenderId;
    }

    public void setPersonnelByEventSenderId(Personnel personnelByEventSenderId) {
        this.personnelByEventSenderId = personnelByEventSenderId;
    }

    public Personnel getPersonnelByPersonnelId() {
        return this.personnelByPersonnelId;
    }

    public void setPersonnelByPersonnelId(Personnel personnelByPersonnelId) {
        this.personnelByPersonnelId = personnelByPersonnelId;
    }

    public boolean isEventIsRead() {
        return eventIsRead;
    }

    public void setEventIsRead(boolean eventIsRead) {
        this.eventIsRead = eventIsRead;
    }

    

}