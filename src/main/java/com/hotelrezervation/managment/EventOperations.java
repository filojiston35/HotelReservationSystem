/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.managment;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.login.LoginControl;
import com.hotelrezervation.mail.SendMail;
import com.hotelrezervation.model.EventDetail;
import com.hotelrezervation.model.EventPersonnel;
import com.hotelrezervation.model.Personnel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.primefaces.PrimeFaces;

/**
 *
 * @author deneme
 */
@ViewScoped
@ManagedBean
public class EventOperations implements Serializable {

    public EventPersonnel event;
    public List<Personnel> personnelList;
    private Session session;
    private Transaction tx1;
    private List<Personnel> selectedPersonel;
    private LoginControl loginControl;
    private List<EventPersonnel> myEventList;
    private List<EventPersonnel> otherEventList;
    private EventPersonnel selectedEvent;
    private List<EventPersonnel> staffInEvenet;
    //Personele gelen bildirim sayısı yukarıda notification da görünecektir.
    private Integer personnelNewEventSize = 0;

    @PostConstruct
    public void init() {
        event = new EventPersonnel();
        event.setEventDetail(new EventDetail());
        getUser();
        viewPersonnel();
        viewMyEvents();
        viewOtherEvents();
    }
    
    public void viewOtherEvents() {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria c = session.createCriteria(EventPersonnel.class);
        c.add(Restrictions.eq("personnelByPersonnelId.personnelId", loginControl.getPersonnel().getPersonnelId()));
        c.add(Restrictions.ne("personnelByEventSenderId.personnelId", loginControl.getPersonnel().getPersonnelId()));
        c.createAlias("eventDetail", "ed");
        c.addOrder(Order.desc("ed.eventCreateDate"));
        otherEventList = c.list();
        session.close();
        newEventSize();
    }

    public void newEventSize() {
        personnelNewEventSize = 0;
        for (EventPersonnel eventPersonnel : otherEventList) {
            if (!eventPersonnel.isEventIsRead()) {
                personnelNewEventSize += 1;
            }
        }
    }

    public void viewMyEvents() {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria c = session.createCriteria(EventPersonnel.class);
        c.add(Restrictions.eq("personnelByPersonnelId.personnelId", loginControl.getPersonnel().getPersonnelId()));
        c.add(Restrictions.eq("personnelByEventSenderId.personnelId", loginControl.getPersonnel().getPersonnelId()));
        c.createAlias("eventDetail", "ed");
        c.addOrder(Order.desc("ed.eventCreateDate"));
        myEventList = c.list();
        session.close();
    }

    public void staffAtMyEvent() {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria c = session.createCriteria(EventPersonnel.class);
        c.add(Restrictions.eq("eventDetail.eventId", selectedEvent.getEventDetail().getEventId()));
        staffInEvenet = c.list();
        session.close();;
    }

    public void getUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        loginControl = application.evaluateExpressionGet(context, "#{loginControl}", LoginControl.class);
    }

    public void viewPersonnel() {
        try {
            personnelList = new ArrayList<Personnel>();
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria c = session.createCriteria(Personnel.class);
            c.add(Restrictions.ne("personnelId", loginControl.getPersonnel().getPersonnelId()));
            personnelList = c.list();
            session.close();
        } catch (Exception e) {

        }

    }

    public void deleteEvent(EventPersonnel sEvent) {
        selectedEvent = sEvent;
        try {
            if (selectedEvent.getPersonnelByEventSenderId().getPersonnelId() == loginControl.getPersonnel().getPersonnelId()) {
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                Query query = session.createQuery("DELETE EventPersonnel WHERE event_sender_id=:sender and event_detail_id=:eventDetail");
                query.setParameter("sender", loginControl.getPersonnel().getPersonnelId());
                query.setParameter("eventDetail", selectedEvent.getEventDetail().getEventId());
                query.executeUpdate();
                tx1.commit();
                session.close();
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                session.delete(selectedEvent.getEventDetail());
                tx1.commit();
                session.close();
            } else {
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                Query query = session.createQuery("DELETE EventPersonnel WHERE personnel_id=:personnel and event_personnel_id=:eventDetail");
                query.setParameter("personnel", loginControl.getPersonnel().getPersonnelId());
                query.setParameter("eventDetail", selectedEvent.getEventPersonnelId());
                query.executeUpdate();
                tx1.commit();
                session.close();

            }
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successDeleteMessage")));
            viewMyEvents();
            viewOtherEvents();
            selectedEvent = new EventPersonnel();

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedDeleteMessage"), ""));
        }
    }

    public void addEevent() {
        try {

            event.getEventDetail().setEventCreateDate(new Date());
            event.setPersonnelByEventSenderId(loginControl.getPersonnel());
            selectedPersonel.add(loginControl.getPersonnel());
            session = HibernateUtil.getSessionFactory().openSession();
            session.save(event.getEventDetail());
            session.close();
            for (Personnel personnel : selectedPersonel) {
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                event.setPersonnelByPersonnelId(personnel);
                session.save(event);
                tx1.commit();
                session.close();
            }
            SendMail sendMail = new SendMail();
            sendMail.newEventMail(selectedPersonel, event.getEventDetail().getEventMessage(), event.getEventDetail().getEventTitle());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successInsertMessage")));
            viewMyEvents();
            viewOtherEvents();
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedInsertMessage"), ""));
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('loadingScreen').hide();");

    }

    public void selectEvent(EventPersonnel event) {
        selectedEvent = event;
        if (selectedEvent.getPersonnelByEventSenderId().getPersonnelId() != loginControl.getPersonnel().getPersonnelId()) {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            event.setEventIsRead(true);
            session.update(event);
            tx1.commit();
            session.close();
        }
        staffAtMyEvent();
        newEventSize();
    }

    public EventPersonnel getEvent() {
        return event;
    }

    public void setEvent(EventPersonnel event) {
        this.event = event;
    }

    public List<Personnel> getPersonnelList() {
        return personnelList;
    }

    public void setPersonnelList(List<Personnel> personnelList) {
        this.personnelList = personnelList;
    }

    public List<Personnel> getSelectedPersonel() {
        return selectedPersonel;
    }

    public void setSelectedPersonel(List<Personnel> selectedPersonel) {
        this.selectedPersonel = selectedPersonel;
    }

    public List<EventPersonnel> getMyEventList() {
        return myEventList;
    }

    public void setMyEventList(List<EventPersonnel> myEventList) {
        this.myEventList = myEventList;
    }

    public List<EventPersonnel> getOtherEventList() {
        return otherEventList;
    }

    public void setOtherEventList(List<EventPersonnel> otherEventList) {
        this.otherEventList = otherEventList;
    }

    public EventPersonnel getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(EventPersonnel selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public LoginControl getLoginControl() {
        return loginControl;
    }

    public void setLoginControl(LoginControl loginControl) {
        this.loginControl = loginControl;
    }

    public List<EventPersonnel> getStaffInEvenet() {
        return staffInEvenet;
    }

    public void setStaffInEvenet(List<EventPersonnel> staffInEvenet) {
        this.staffInEvenet = staffInEvenet;
    }

    public Integer getPersonnelNewEventSize() {
        return personnelNewEventSize;
    }

    public void setPersonnelNewEventSize(Integer personnelNewEventSize) {
        this.personnelNewEventSize = personnelNewEventSize;
    }

}
