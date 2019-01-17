/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.components;

import com.hotelrezervation.model.Reservation;
import com.hotelrezervation.model.Rooms;

/**
 *
 * @author deneme
 */
public class RoomActions {
    
    private Reservation outReservation;
    private Reservation InReservation;
    private Rooms room;

    public Reservation getOutReservation() {
        return outReservation;
    }

    public void setOutReservation(Reservation outReservation) {
        this.outReservation = outReservation;
    }

    public Reservation getInReservation() {
        return InReservation;
    }

    public void setInReservation(Reservation InReservation) {
        this.InReservation = InReservation;
    }

    public Rooms getRoom() {
        return room;
    }

    public void setRoom(Rooms room) {
        this.room = room;
    }
    
    
}
