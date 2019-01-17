/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.managment;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.model.Reservation;
import com.hotelrezervation.model.Rooms;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author deneme
 */
@ManagedBean
@ViewScoped
public class RoomOperations implements Serializable {

    private Session session;
    private Transaction tx1;
    private List<Rooms> rooms;
    private Map<String, Integer> hashRooms;
    private Rooms selectedRoom;

    @PostConstruct
    public void init() {

        rooms = new ArrayList<Rooms>();
        hashRooms = new HashMap<String, Integer>();
        selectedRoom = new Rooms();
        viewRooms();
    }

    public void viewRooms() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            rooms = session.createCriteria(Rooms.class).list();
            hashRooms = new HashMap<String, Integer>();
            for (Rooms r : rooms) {
                hashRooms.put(r.getRoomName(), r.getRoomId());
            }
            tx1.commit();
            session.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void insertRoom() {
        Boolean temp = false;
        try {
            for (Rooms r : rooms) {
                if (r.getRoomNo() == selectedRoom.getRoomNo()) {
                    temp = true;
                    break;
                }
            }
            if (temp.equals(false)) {
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                Integer id = (Integer) session.save(selectedRoom);
                tx1.commit();
                viewRooms();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
                session.close();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("sameRoomMessage"), ""));
            }
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
        }
    }

    public void selectedRoomEmpty() {
        selectedRoom = new Rooms();
    }

    public void updateRoom() {
        Boolean temp = false;
        try {
            for (Rooms r : rooms) {
                if (r.getRoomNo() == selectedRoom.getRoomNo() && r.getRoomId() != selectedRoom.getRoomId()) {
                    temp = true;
                    break;
                }
            }
            if (temp.equals(false)) {
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                session.update(selectedRoom);
                tx1.commit();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
                session.close();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("sameRoomMessage"), ""));
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
        }
        viewRooms();
    }

    public void deleteRoom(Rooms r) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            Query query = session.createQuery("FROM Reservation WHERE room_id=:room_id");
            query.setParameter("room_id", r.getRoomId());
            List<Reservation> hasReservationInRoom = (List<Reservation>) query.getResultList();
            if (hasReservationInRoom.size() == 0) {
                session.delete(r);
                rooms.remove(r);
                tx1.commit();
                session.close();
                viewRooms();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successDeleteMessage")));
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("fullRoomMessage"), ""));
            }

            session.close();
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedDeleteMessage"), ""));
        }
    }

    public List<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(List<Rooms> rooms) {
        this.rooms = rooms;
    }

    public Map<String, Integer> getHashRooms() {
        return hashRooms;
    }

    public void setHashRooms(Map<String, Integer> hashRooms) {
        this.hashRooms = hashRooms;
    }

    public Rooms getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Rooms selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

}
