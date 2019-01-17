/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.all.personnel;

import com.hotelrezervation.components.RoomActions;
import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.model.Reservation;
import com.hotelrezervation.model.Rooms;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author deneme
 */
@ViewScoped
@ManagedBean
public class HomePage implements Serializable {

    private Date now;
    private Session session;
    private Transaction tx1;
    private List<Rooms> rooms;
    private List<RoomActions> roomActions;
    private Reservation selectedReservation;

    @PostConstruct
    public void init() {
        now = new Date();
        rooms = new ArrayList<Rooms>();
        roomActions = new ArrayList<RoomActions>();
        viewRooms();
        customRoomActions();
    }

    public void viewRooms() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            rooms = session.createCriteria(Rooms.class).list();
            tx1.commit();
            session.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void customRoomActions() {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar nowCalendar = Calendar.getInstance();
        RoomActions tempRoomActions;
        for (Rooms room : rooms) {
            tempRoomActions = new RoomActions();
            tempRoomActions.setRoom(room);
            Object actions[] = room.getReservations().toArray();
            for (int i = 0; i < actions.length; i++) {
                Reservation tempReservation = (Reservation) actions[i];
                startDate.setTime(tempReservation.getReservationStartDate());
                endDate.setTime(tempReservation.getReservationEndDate());
                if (startDate.get(Calendar.DAY_OF_MONTH) == nowCalendar.get(Calendar.DAY_OF_MONTH) && startDate.get(Calendar.DAY_OF_YEAR) == nowCalendar.get(Calendar.DAY_OF_YEAR) && startDate.get(Calendar.MONTH) == nowCalendar.get(Calendar.MONTH)) {
                    tempRoomActions.setInReservation(tempReservation);
                } else if (endDate.get(Calendar.DAY_OF_MONTH) == nowCalendar.get(Calendar.DAY_OF_MONTH) && endDate.get(Calendar.DAY_OF_YEAR) == nowCalendar.get(Calendar.DAY_OF_YEAR) && endDate.get(Calendar.MONTH) == nowCalendar.get(Calendar.MONTH)) {
                    tempRoomActions.setOutReservation(tempReservation);
                }

            }
            roomActions.add(tempRoomActions);
        }
    }

    public Reservation getSelectedReservation() {
        return selectedReservation;
    }

    public void setSelectedReservation(Reservation selectedReservatiom) {
        this.selectedReservation = selectedReservatiom;
    }

    public List<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(List<Rooms> rooms) {
        this.rooms = rooms;
    }

    public List<RoomActions> getRoomActions() {
        return roomActions;
    }

    public void setRoomActions(List<RoomActions> roomActions) {
        this.roomActions = roomActions;
    }

}
