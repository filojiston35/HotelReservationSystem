/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.personel.settings;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.core.TokenGenerator;
import com.hotelrezervation.mail.SendMail;
import com.hotelrezervation.model.Personnel;
import com.hotelrezervation.model.MailToken;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.primefaces.PrimeFaces;

/**
 *
 * @author deneme
 */
@ManagedBean
@ViewScoped
public class ForgotPassword implements Serializable {

    private String personnel_Mail;
    private Personnel personnel;

    public void hasMail() {
        if (hasTokenPersonnel(personnel_Mail)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedCheckMail"), ""));
        } else {
            try {
                Session session = HibernateUtil.getSessionFactory().openSession();
                Query query = session.createQuery("FROM Personnel WHERE personnel_Mail=:personnel_Mail");
                query.setParameter("personnel_Mail", personnel_Mail);
                personnel = (Personnel) query.getSingleResult();
                session.beginTransaction().commit();
                session.close();
                if (personnel == null) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("errorMail"), ""));
                } else {
                    SendMail sendMail = new SendMail();
                    TokenGenerator tg = new TokenGenerator(personnel_Mail);
                    String token = tg.addToken();
                    sendMail.forgotPassword(token, personnel_Mail);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successCheckMail")));

                }
            } catch (Exception e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("errorMail"), ""));
                System.out.println(e);
            }
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('loadingScreen').hide();");

    }

    public boolean hasTokenPersonnel(String personnel_Mail) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM MailToken T");
        List<MailToken> token_list = (List<MailToken>) query.getResultList();
        for (MailToken t : token_list) {
            if (t.getTokenMail().equals(personnel_Mail)) {
                return true;
            }
        }
        session.close();
        return false;
    }

    public String getPersonnel_Mail() {
        return personnel_Mail;
    }

    public void setPersonnel_Mail(String personnel_Mail) {
        this.personnel_Mail = personnel_Mail;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

}
