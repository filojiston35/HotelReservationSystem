/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.managment;

import com.hotelrezervation.components.DateFormat;
import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.model.Adisyon;
import com.hotelrezervation.model.Reservation;
import com.hotelrezervation.model.Rooms;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;
import java.util.Calendar;
import java.util.Locale;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.query.Query;

/**
 *
 * @author deneme
 */
@ManagedBean
@ViewScoped
public class ReservationOperations implements Serializable {

    private TimelineModel timeLineModel;
    private TimelineEvent selectedEvent;
    private Date timeLineStart;
    private Date timeLineEnd;
    private Date now;
    private Map<String, Integer> hashRooms;
    private Session session;
    private Transaction tx1;
    private List<Reservation> reservationList;
    private Reservation selectedReservation;
    private String reservationDescription="";

    @PostConstruct
    public void init() {
        hashRooms = new HashMap<String, Integer>();
        selectedEvent = new TimelineEvent();
        now = new Date();
        timeLineStart=now;
        timeLineEnd=now;
        selectedRoomReservationEmpty();
        viewReservations();
        viewRooms();
    }

    public void viewReservations() {
        try {
            timeLineModel = new TimelineModel();
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            reservationList = session.createCriteria(Reservation.class).list();
            TimelineEvent event;
            for (Reservation reservation : reservationList) {
                String roomName;
                String status;
                if (reservation.getRooms().getRoomName().equals(null)) {
                    roomName = reservation.getRooms().getRoomId().toString();
                } else {
                    roomName = reservation.getRooms().getRoomId() + " - " + reservation.getRooms().getRoomName();
                }
                if (reservation.getReservationEndDate().before(now)) {
                    status = "unavailable";
                } else if (reservation.getReservationStartDate().before(now) && reservation.getReservationEndDate().after(now)) {
                    status = "available";
                } else {
                    status = "maybe";
                }
                event = new TimelineEvent(reservation,
                        reservation.getReservationStartDate(),
                        reservation.getReservationEndDate(),
                        false,
                        roomName,
                        status);
                timeLineModel.add(event);
            }
            session.close();
        } catch (Exception e) {
        }
    }

    public void viewRooms() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            List<Rooms> rooms = session.createCriteria(Rooms.class).list();
            hashRooms = new HashMap<String, Integer>();
            for (Rooms r : rooms) {
                hashRooms.put(r.getRoomNo() + " - " + r.getRoomName(), r.getRoomId());
            }
            tx1.commit();
            session.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void getReservationRoom() {
        session = HibernateUtil.getSessionFactory().openSession();
        tx1 = session.beginTransaction();
        Integer tempRoomId = selectedReservation.getRooms().getRoomId();
        selectedReservation.setRooms(session.get(Rooms.class, tempRoomId));
        tx1.commit();
        session.close();
    }

    public void insertReservation() {
        Boolean temp = false;
        try {
            getReservationRoom();
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            selectedReservation.setReservationCreateDate(new Date());
            //Aynı tarihlerde rezervasyon var mı
            Calendar NRS = Calendar.getInstance();
            Calendar NRE = Calendar.getInstance();
            Calendar ORS = Calendar.getInstance();
            Calendar ORE = Calendar.getInstance();

            NRS.setTime(selectedReservation.getReservationStartDate());
            NRE.setTime(selectedReservation.getReservationEndDate());

            for (Reservation reservation : reservationList) {

                if (selectedReservation.getRooms().getRoomId() == reservation.getRooms().getRoomId()) {
                    ORS.setTime(reservation.getReservationStartDate());
                    ORE.setTime(reservation.getReservationEndDate());

                    if (ORS.equals(NRS)) {
                        temp = true;
                        break;
                    } else if ((ORS.before(NRE) && NRE.before(ORE)) || ORS.before(NRS) && NRS.before(ORE)) {
                        temp = true;
                        break;
                    } else if (NRS.before(ORS) && ORS.before(NRE)) {
                        temp = true;
                        break;
                    }
                }
            }
            if (reservationDateControl()) {
                if (temp.equals(false)) {
                    Integer id = (Integer) session.save(selectedReservation);
                    tx1.commit();
                    tx1 = session.beginTransaction();
                    Adisyon adisyon = new Adisyon();
                    adisyon.setReservation(session.get(Reservation.class, id));
                    session.save(adisyon);
                    tx1.commit();
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successInsertMessage")));
                    session.close();
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYY");
                    if (FacesContext.getCurrentInstance().getViewRoot().getLocale().equals(new Locale("tr"))) {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, format.format(NRS.getTime()) + " - " + format.format(NRE.getTime()) + " tarihleri arasında " + selectedReservation.getRooms().getRoomNo() + " numaralı odada başka bir rezervasyon var.", ""));
                    } else if (FacesContext.getCurrentInstance().getViewRoot().getLocale().equals(new Locale("en"))) {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Room " + selectedReservation.getRooms().getRoomNo() + " has a reservation between " + format.format(NRS.getTime()) + " - " + format.format(NRE.getTime()), ""));
                    }

                }
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("reservationStartEndDateControl"), ""));
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedInsertMessage"), ""));
        }
        viewReservations();
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (Exception e) {
        }
    }

    public void updateReservation() {
        Boolean temp = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();

            Calendar NRS = Calendar.getInstance();
            Calendar NRE = Calendar.getInstance();
            Calendar ORS = Calendar.getInstance();
            Calendar ORE = Calendar.getInstance();

            NRS.setTime(selectedReservation.getReservationStartDate());
            NRE.setTime(selectedReservation.getReservationEndDate());
            String resRoom = null;
            for (Reservation reservation : reservationList) {
                resRoom = reservation.getRooms().getRoomName();
                if (selectedReservation.getRooms().getRoomId() == reservation.getRooms().getRoomId() && selectedReservation.getReservationId() != reservation.getReservationId()) {
                    ORS.setTime(reservation.getReservationStartDate());
                    ORE.setTime(reservation.getReservationEndDate());

                    if (ORS.equals(NRS)) {
                        temp = true;
                        break;
                    } else if ((ORS.before(NRE) && NRE.before(ORE)) || ORS.before(NRS) && NRS.before(ORE)) {
                        temp = true;
                        break;
                    } else if (NRS.before(ORS) && ORS.before(NRE)) {
                        temp = true;
                        break;
                    }
                }
            }
            if (temp.equals(false)) {
                session.update(selectedReservation);
                tx1.commit();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
                session.close();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYY");
                if (FacesContext.getCurrentInstance().getViewRoot().getLocale().equals(new Locale("tr"))) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, format.format(NRS.getTime()) + " - " + format.format(NRE.getTime()) + " tarihleri arasında " + selectedReservation.getRooms().getRoomNo() + " numaralı odada başka bir rezervasyon var.", ""));
                } else if (FacesContext.getCurrentInstance().getViewRoot().getLocale().equals(new Locale("en"))) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Room " + selectedReservation.getRooms().getRoomNo() + " has a reservation between " + format.format(NRS.getTime() + " - " + NRE.getTime()), ""));
                }

            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
        }
        viewReservations();
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (Exception e) {
        }
    }

    public void deleteReservationRoom() {
        try {
            //ÖNCE ADİSYON İÇİNDEKİ ÜRÜNLER SİLİNECEK
            Adisyon[] tempAdisyon = (Adisyon[]) selectedReservation.getAdisyons().toArray(new Adisyon[selectedReservation.getAdisyons().size()]);
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            Query selectProductInAdisyon = session.createQuery("DELETE AdisyonProducts WHERE adisyon_id=:adisyon_id");
            selectProductInAdisyon.setParameter("adisyon_id", tempAdisyon[0].getAdisyonId());
            selectProductInAdisyon.executeUpdate();
            tx1.commit();
            session.close();
            //SONRA ADİSYON VE REZERVASYON SİLİNECEK.
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            Query query = session.createQuery("DELETE Adisyon WHERE reservation_id=:reservation_id");
            query.setParameter("reservation_id", selectedReservation.getReservationId());
            query.executeUpdate();
            session.delete(selectedReservation);
            tx1.commit();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successDeleteMessage")));
            session.close();
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedDeleteMessage"), ""));
        }
        viewReservations();
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (Exception e) {
        }
    }

    public boolean reservationDateControl() {
        DateFormat df = new DateFormat();
        return df.twoDateComparison(selectedReservation.getReservationStartDate(), selectedReservation.getReservationEndDate());
    }

    public void onSelect(TimelineSelectEvent e) {
        selectedEvent = e.getTimelineEvent();
        selectedReservation = (Reservation) selectedEvent.getData();
        reservationDescription=selectedReservation.getReservationComment();
    }

    public String reservationDescription() {
        return reservationDescription;
    }

    public void selectedRoomReservationEmpty() {
        Rooms selectedRoom = new Rooms();
        selectedReservation = new Reservation();
        selectedReservation.setRooms(selectedRoom);
    }

    public TimelineModel getTimeLineModel() {
        return timeLineModel;
    }

    public void setTimeLineModel(TimelineModel timeLineModel) {
        this.timeLineModel = timeLineModel;
    }

    public Date getTimeLineStart() {
        return timeLineStart;
    }

    public void setTimeLineStart(Date timeLineStart) {
        this.timeLineStart = timeLineStart;
    }

    public Date getTimeLineEnd() {
        return timeLineEnd;
    }

    public void setTimeLineEnd(Date timeLineEnd) {
        this.timeLineEnd = timeLineEnd;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public Reservation getSelectedReservation() {
        return selectedReservation;
    }

    public void setSelectedReservation(Reservation selectedReservation) {
        this.selectedReservation = selectedReservation;
    }

    public Map<String, Integer> getHashRooms() {
        return hashRooms;
    }

    public void setHashRooms(Map<String, Integer> hashRooms) {
        this.hashRooms = hashRooms;
    }

    public String getReservationDescription() {
        return reservationDescription;
    }

    public void setReservationDescription(String reservationDescription) {
        this.reservationDescription = reservationDescription;
    }
    
}
