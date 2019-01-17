/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.personel.settings;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.login.LoginControl;
import com.hotelrezervation.model.Departments;
import com.hotelrezervation.model.Personnel;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.Part;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author deneme
 */
@ManagedBean
@ViewScoped
public class Profile implements Serializable {

    private LoginControl loginControl;
    private Session session;
    private Departments department;
    private Transaction tx1;
    private Part image;
    private String imageName;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        loginControl = application.evaluateExpressionGet(context, "#{loginControl}", LoginControl.class);
        pullDepartment();

    }

    public void pullDepartment() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Departments D where departments_id=:departments_id");
            query.setParameter("departments_id", loginControl.getPersonnel().getDepartments().getDepartmentsId());
            department = (Departments) query.getSingleResult();
            session.beginTransaction().commit();
            session.close();
            loginControl.getPersonnel().setDepartments(department);
        } catch (Exception e) {
        }
    }

    public String updatexPersonnel() {//Personel güncellemek için
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            if (!doUpload()) {
                session.update(loginControl.getPersonnel());
                tx1.commit();
                session.close();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                return "profile.xhtml?faces-redirect=true";
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
        }
        return "";
    }

    public Boolean doUpload() {
        Boolean temp = false;
        try {
            if (image != null) {
                InputStream in = image.getInputStream();
                if (!image.getContentType().contains("png") && !image.getContentType().contains("jpeg")) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("xwrongFileType"), ""));
                    temp = true;

                }
                if (image.getSize() > 2000000) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("xwrongPhotoSize"), ""));
                    temp = true;
                }
                if (temp.equals(false)) {
                    File f = new File("/home/deneme/NetBeansProjects/hotel-rezervation/src/main/webapp/resources/upload/" + image.getSubmittedFileName());
                    f.createNewFile();
                    FileImageOutputStream out = new FileImageOutputStream(f);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                    out.close();
                    in.close();
                    loginControl.getPersonnel().setPersonnelImage(image.getSubmittedFileName());
                    System.out.println(image.getSubmittedFileName());
                }

            }

        } catch (Exception e) {
            System.out.println(e);
            temp = true;
        }
        return temp;
    }

    public LoginControl getLoginControl() {
        return loginControl;
    }

    public void setLoginControl(LoginControl loginControl) {
        this.loginControl = loginControl;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}
