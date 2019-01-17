/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.login;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.PasswordEncryption;
import com.hotelrezervation.model.Personnel;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author deneme
 */
@ManagedBean(name = "loginControl")
@ApplicationScoped
public class LoginControl implements Serializable {

    private String personnel_Mail;
    private String personnel_Password;
    private Personnel personnel;
    private Boolean errorMessage = false;
    private Boolean isLoggedin = false;
    private String EncryptedPassword;   

    public String connect() {
        try {
            EncryptedPassword = PasswordEncryption.passwordEncrypt(personnel_Password);
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Personnel WHERE personnel_Mail=:personnel_Mail AND personnel_Password=:personnel_Password");
            query.setParameter("personnel_Mail", personnel_Mail);
            query.setParameter("personnel_Password", EncryptedPassword);
            personnel = (Personnel) query.getSingleResult();
            session.beginTransaction().commit();
            session.close();
            errorMessage = false;
            isLoggedin = true;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", personnel);
            return "secured/home.xhtml?faces-redirect=true";
        } catch (Exception e) {
            errorMessage = true;
            System.out.println(e);
            return "login.xhtml?faces-redirect=true";
        }
    }
    

    public void logout() throws IOException {
        isLoggedin = false;
        FacesContext.getCurrentInstance().getExternalContext().redirect("logout.xhtml");
    }

    public String getPersonnel_Mail() {
        return personnel_Mail;
    }

    public void setPersonnel_Mail(String personnel_Mail) {
        this.personnel_Mail = personnel_Mail;
    }

    public String getPersonnel_Password() {
        return personnel_Password;
    }

    public void setPersonnel_Password(String personnel_Password) {
        this.personnel_Password = personnel_Password;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Boolean getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Boolean errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getIsLoggedin() {
        return isLoggedin;
    }

    public void setIsLoggedin(Boolean isLoggedin) {
        this.isLoggedin = isLoggedin;
    }

}
