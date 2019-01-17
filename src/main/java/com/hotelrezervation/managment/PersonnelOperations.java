/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.managment;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.core.PasswordEncryption;
import com.hotelrezervation.core.PasswordGenerator;
import com.hotelrezervation.login.LoginControl;
import com.hotelrezervation.mail.SendMail;
import com.hotelrezervation.model.Departments;
import com.hotelrezervation.model.Personnel;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.primefaces.PrimeFaces;

/**
 *
 * @author deneme
 */
@ManagedBean(name = "personnelOperations")
@ViewScoped
public class PersonnelOperations implements Serializable {

    private List<Personnel> personnel;
    private Personnel selectedPersonel;
    private Session session;
    private Transaction tx1;
    private SendMail sendMail;

    @PostConstruct
    public void init() {
        selectedPersonelEmpty();
        viewPersonnel();
    }

    public void viewPersonnel() {//TÜm personnelleri görüntülemek için
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria c = session.createCriteria(Personnel.class);
            personnel = c.list();
            session.close();
        } catch (Exception e) {
        }

    }

    public void addNewPersonnel() {//Yeni bir personel eklemek için
        try {

            String generatedPassword = PasswordGenerator.passwordGenerator();
            selectedPersonel.setPersonnelPassword(PasswordEncryption.passwordEncrypt(generatedPassword));
            selectedPersonel.setPersonnelAdmin(false);
            selectedPersonel.setPersonnelImage("user-alt-solid.svg");
            if (mailControl(selectedPersonel.getPersonnelMail()).equals(false)) {
                session = HibernateUtil.getSessionFactory().openSession();
                Integer id = (Integer) session.save(selectedPersonel);
                session.close();
                viewPersonnel();
                sendMail = new SendMail();
                sendMail.newPersonnel(selectedPersonel.getPersonnelMail(), selectedPersonel.getPersonnelName() + " " + selectedPersonel.getPersonnelSurname(), generatedPassword);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successInsertMessage")));
                selectedPersonelEmpty();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("sameMailMessage"), ""));
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedInsertMessage"), ""));
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('loadingScreen').hide();");

    }

    public Boolean mailControl(String mail) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Personnel WHERE personnel_Mail=:personnel_Mail");
            query.setParameter("personnel_Mail", selectedPersonel.getPersonnelMail());
            List<Personnel> tempPersonel = (List<Personnel>) query.getResultList();
            session.close();
            if (tempPersonel.size() != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void deletePersonnel(Personnel p) {//Personel silmek için
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            Query query = session.createQuery("DELETE EventPersonnel WHERE personnel_id=:pid");
            query.setParameter("pid", p.getPersonnelId());
            query.executeUpdate();
            tx1.commit();
            session.close();

            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            session.delete(p);
            personnel.remove(p);
            tx1.commit();
            session.close();
            viewPersonnel();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successDeleteMessage")));
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedDeleteMessage"), ""));
        }
    }

    public void updatexPersonnel() {//Personel güncellemek için
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            session.update(selectedPersonel);
            tx1.commit();
            session.close();
            viewPersonnel();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
        }
    }

    public void selectedPersonelEmpty()//selectedPersonel variable nı boşaltmak için
    {
        selectedPersonel = new Personnel();
        Departments department = new Departments();
        selectedPersonel.setDepartments(department);
    }

    public List<Personnel> getPersonnel() {
        return personnel;
    }

    public void setPersonnel(List<Personnel> personnel) {
        this.personnel = personnel;
    }

    public Personnel getSelectedPersonel() {
        return selectedPersonel;
    }

    public void setSelectedPersonel(Personnel selectedPersonel) {
        this.selectedPersonel = selectedPersonel;
    }

}
