/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.managment;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.model.Departments;
import com.hotelrezervation.model.Personnel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
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
@ManagedBean(name = "departmentOperations")
@ViewScoped
public class DepartmentOperations implements Serializable {

    private List<Departments> departments;
    private Departments selectedDepartments;
    private Map<String, Integer> hashDepartments;
    private Transaction tx1;
    private Query query;
    private Session session;
    private String updateName;
    private Integer updateId;
    private Set<Personnel> selectedPersonnel;
    private Personnel person;

    @PostConstruct
    public void init() {
        session = HibernateUtil.getSessionFactory().openSession();
        selectedDepartments = new Departments();
        departments = new ArrayList<Departments>();
        viewDepartments();
    }

    public void viewDepartments() {
        try {
            session.close();
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            departments = session.createCriteria(Departments.class).list();
            hashDepartments = new HashMap<String, Integer>();
            for (Departments dep : departments) {
                hashDepartments.put(dep.getDepartmentsName().toUpperCase(), dep.getDepartmentsId());
            }
            tx1.commit();
        } catch (Exception e) {
        }

    }

    public void viewPersonnelInDepartment(Departments d) {
        selectedDepartments = d;
        selectedPersonnel = selectedDepartments.getPersonnels();
    }

    public void updateDepartments() {
        try {
            Boolean temp = false;
            for (Departments department : departments) {
                if (department.getDepartmentsName().toLowerCase().equals(updateName.toLowerCase()) && department.getDepartmentsId() != updateId) {
                    temp = true;
                    break;
                }
            }
            if (temp.equals(false)) {
                tx1 = session.beginTransaction();
                query = session.createQuery("update Departments set departments_name=:updateName where departments_id=:updateId");
                query.setParameter("updateName", updateName);
                query.setParameter("updateId", updateId);
                query.executeUpdate();
                tx1.commit();
                viewDepartments();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,OperationInternalizationMessage.operationMesssage("sameDepartmentMessage"), ""));
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
        }

    }

    public void deleteDepartments(Departments d) {
        try {
            Set<Personnel> personnelList = d.getPersonnels();
            System.out.println(personnelList.size());
            if (!personnelList.isEmpty()) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,OperationInternalizationMessage.operationMesssage("fullDepartmentMessage"), ""));
            } else {
                tx1 = session.beginTransaction();
                session.delete(d);
                tx1.commit();
                viewDepartments();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successDeleteMessage")));
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,OperationInternalizationMessage.operationMesssage("failedDeleteMessage"), ""));
        }
    }

    public void insertDepartments() {
        try {
            Boolean temp = false;
            selectedDepartments.setDepartmentsName(updateName);
            for (Departments department : departments) {
                if (department.getDepartmentsName().toLowerCase().equals(selectedDepartments.getDepartmentsName().toLowerCase())) {
                    temp = true;
                    break;
                }
            }
            if (temp.equals(false)) {
                tx1 = session.beginTransaction();
                Integer id = (Integer) session.save(selectedDepartments);
                tx1.commit();
                viewDepartments();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successInsertMessage")));
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,OperationInternalizationMessage.operationMesssage("sameDepartmentMessage"), ""));
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,OperationInternalizationMessage.operationMesssage("failedInsertMessage"), ""));
        }
    }

    public void setSelectedDepartmentWithUpdate(Departments d) {
        updateName = d.getDepartmentsName();
        updateId = d.getDepartmentsId();
    }

    public void selectedDepartmentEmpty() {
        selectedDepartments = new Departments();
        updateName = null;
        updateId = null;
    }

    public List<Departments> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Departments> departments) {
        this.departments = departments;
    }

    public Departments getSelectedDepartments() {
        return selectedDepartments;
    }

    public void setSelectedDepartments(Departments selectedDepartments) {
        this.selectedDepartments = selectedDepartments;
    }

    public Map<String, Integer> getHashDepartments() {
        return hashDepartments;
    }

    public void setHashDepartments(Map<String, Integer> hashDepartments) {
        this.hashDepartments = hashDepartments;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Set<Personnel> getSelectedPersonnel() {
        return selectedPersonnel;
    }

    public void setSelectedPersonnel(Set<Personnel> selectedPersonnel) {
        this.selectedPersonnel = selectedPersonnel;
    }

    public Personnel getPerson() {
        return person;
    }

    public void setPerson(Personnel person) {
        this.person = person;
    }
}
