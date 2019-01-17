/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.managment;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.model.Adisyon;
import com.hotelrezervation.model.AdisyonProducts;
import com.hotelrezervation.model.ProductCategories;
import com.hotelrezervation.model.Products;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author deneme
 */
@ViewScoped
@ManagedBean
public class AdisyonOperations implements Serializable {

    private Session session;
    private Transaction tx1;
    private List<Adisyon> adisyonList;
    private List<AdisyonProducts> adisyonProductsList;
    private List<AdisyonProducts> selectedAdisyonProducts;
    private Date now;
    private Adisyon selectedAdisyon;
    private AdisyonProducts selectedProducts;
    private Double totalPrice = 0.0;

    @PostConstruct
    public void init() {
        now = new Date();
        adisyonList = new ArrayList<Adisyon>();
        adisyonProductsList = new ArrayList<AdisyonProducts>();
        viewAdisyon();
        selectedProductEmpty();

    }

    public void viewAdisyon() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            Criteria c = session.createCriteria(Adisyon.class);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String tempDate = format.format(now);
            c.createAlias("reservation", "res");
            c.add(Restrictions.le("res.reservationStartDate", format.parse(tempDate)));
            c.add(Restrictions.ge("res.reservationEndDate", format.parse(tempDate)));
            c.addOrder(Order.desc("res.reservationStartDate"));
            adisyonList = c.list();
            tx1.commit();
            session.close();
        } catch (Exception e) {
        }

    }

    public void insertProductInAdisyon() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            selectedProducts.setPurchaseDate(new Date());

            selectedProducts.setAdisyon(selectedAdisyon);
            Integer Id = (Integer) session.save(selectedProducts);
            tx1.commit();
            session.close();
            viewAdisyon();
            refreshAdisyonList();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successInsertMessage")));

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,OperationInternalizationMessage.operationMesssage("failedInsertMessage"),""));
        }

    }

    public void updateProductInAdisyon() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            session.update(selectedProducts);
            tx1.commit();
            session.close();
            viewAdisyon();
            refreshAdisyonList();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
        }
    }

    public void deleteProductInAdisyon(AdisyonProducts ai) {
        selectedProducts = ai;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            session.delete(selectedProducts);
            tx1.commit();
            session.close();
            viewAdisyon();
            refreshAdisyonList();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successDeleteMessage")));
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedDeleteMessage"), ""));
        }
    }

    public void refreshAdisyonList() {
        Integer tempId = selectedAdisyon.getAdisyonId();
        for (Adisyon adisyon : adisyonList) {
            if (adisyon.getAdisyonId() == tempId) {
                viewAdisyonProducts(adisyon);
                break;
            }
        }
    }

    public void viewAdisyonProducts(Adisyon adisyon) {
        selectedAdisyon = new Adisyon();
        selectedProducts = new AdisyonProducts();

        selectedAdisyon = adisyon;
        selectedAdisyonProducts=new ArrayList<AdisyonProducts>();
        
        Set<AdisyonProducts>temp = selectedAdisyon.getAdisyonProductses();
        totalPrice = 0.0;
        for (AdisyonProducts selectedAdisyonProduct : temp) {          
            totalPrice += selectedAdisyonProduct.getProducts().getProductPrice() * selectedAdisyonProduct.getProductQuantity();
        }
        
            session = HibernateUtil.getSessionFactory().openSession();
            Query query=session.createQuery("FROM AdisyonProducts WHERE adisyon_id=:adisyon_id");
            query.setParameter("adisyon_id", selectedAdisyon.getAdisyonId());
            selectedAdisyonProducts=(List<AdisyonProducts>)query.getResultList();
            session.close();
    }

    public void selectedProductEmpty() {
        selectedProducts = new AdisyonProducts();
        selectedProducts.setProducts(new Products());
        selectedProducts.getProducts().setProductCategories(new ProductCategories());
    }

    public List<Adisyon> getAdisyonList() {
        return adisyonList;
    }

    public void setAdisyonList(List<Adisyon> adisyonList) {
        this.adisyonList = adisyonList;
    }

    public List<AdisyonProducts> getAdisyonProductsList() {
        return adisyonProductsList;
    }

    public void setAdisyonProductsList(List<AdisyonProducts> adisyonProductsList) {
        this.adisyonProductsList = adisyonProductsList;
    }

    public List<AdisyonProducts> getSelectedAdisyonProducts() {
        return selectedAdisyonProducts;
    }

    public void setSelectedAdisyonProducts(List<AdisyonProducts> selectedAdisyonProducts) {
        this.selectedAdisyonProducts = selectedAdisyonProducts;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public Adisyon getSelectedAdisyon() {
        return selectedAdisyon;
    }

    public void setSelectedAdisyon(Adisyon selectedAdisyon) {
        this.selectedAdisyon = selectedAdisyon;
    }

    public AdisyonProducts getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(AdisyonProducts selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
