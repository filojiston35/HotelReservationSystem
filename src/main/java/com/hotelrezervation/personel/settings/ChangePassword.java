/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.personel.settings;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.core.PasswordEncryption;
import com.hotelrezervation.login.LoginControl;
import com.hotelrezervation.model.Personnel;
import com.hotelrezervation.model.MailToken;
import java.util.Date;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
public class ChangePassword {

    private String oldPassword;
    private String newPassword;
    private String checkNewPassword;
    private Session session;
    private LoginControl loginControl;
    private Transaction tx1;
    private String token_code;
    private MailToken token;

    public void changePass() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        loginControl = application.evaluateExpressionGet(context, "#{loginControl}", LoginControl.class);
        Personnel personnel = loginControl.getPersonnel();
        token_code = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

        if (!newPassword.equals(checkNewPassword)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedControlPassword"), ""));
        } else {
            try {
                if (!token_code.equals("")) {
                    tokenDateControl();
                    if (!hasToken()) {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedOldLinkPassword"), ""));
                    } else {
                        session = HibernateUtil.getSessionFactory().openSession();
                        tx1 = session.beginTransaction();
                        Query query = session.createQuery("update Personnel set personnel_password=:personnel_password where personnel_mail=:personnel_mail");
                        query.setParameter("personnel_password", PasswordEncryption.passwordEncrypt(newPassword));
                        query.setParameter("personnel_mail", token.getTokenMail());
                        int result = query.executeUpdate();

                        tx1.commit();
                        session.close();
                        context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
                        FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
                    }

                } else {
                    if (!PasswordEncryption.passwordEncrypt(oldPassword).equals(loginControl.getPersonnel().getPersonnelPassword())) {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedOldPassword"), ""));
                    } else {
                        session = HibernateUtil.getSessionFactory().openSession();
                        tx1 = session.beginTransaction();
                        personnel.setPersonnelPassword(PasswordEncryption.passwordEncrypt(newPassword));
                        session.merge(personnel);

                        tx1.commit();
                        session.close();
                        context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
            }
        }
    }

    public boolean hasToken() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            Query query = session.createQuery("FROM MailToken WHERE token=:token");
            query.setParameter("token", token_code);
            token = (MailToken) query.getSingleResult();
            session.close();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void tokenDateControl() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            Query query = session.createQuery("From MailToken T");
            List<MailToken> tokensList = (List<MailToken>) query.getResultList();

            for (MailToken t : tokensList) {
                Date token_date = t.getTokenDate();
                Date now = new Date();

                long diff = now.getTime() - token_date.getTime();

                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;

                if (diffMinutes >= 15 || diffHours > 0) {
                    session.delete(t);
                }
            }
            tx1.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCheckNewPassword() {
        return checkNewPassword;
    }

    public void setCheckNewPassword(String checkNewPassword) {
        this.checkNewPassword = checkNewPassword;
    }

    public String getToken_code() {
        return token_code;
    }

    public void setToken_code(String token_code) {
        this.token_code = token_code;
    }

}
