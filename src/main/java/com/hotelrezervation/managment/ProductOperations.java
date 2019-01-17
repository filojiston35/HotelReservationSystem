/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.managment;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.model.ProductCategories;
import com.hotelrezervation.model.Products;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

/**
 *
 * @author deneme
 */
@ViewScoped
@ManagedBean
public class ProductOperations implements Serializable {

    private List<ProductCategories> productCategories;
    private List<Products> products;
    private Session session;
    private Transaction tx1;
    private Query query;
    private Map<String, Integer> hashProductCategories;
    private Map<String, Integer> hashProducts;
    private ProductCategories filterProductCategory;
    private Products selectedProduct;
    private ProductCategories selectedProductsCategory;

    @PostConstruct
    public void init() {
        productCategories = new ArrayList<ProductCategories>();
        products = new ArrayList<Products>();
        selectedProduct = new Products();
        selectedProductsCategory = new ProductCategories();
        filterProductCategory = new ProductCategories();
        viewProductCategories();
        viewProducts();
    }

    public void viewProductCategories() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            productCategories = session.createCriteria(ProductCategories.class).list();
            hashProductCategories = new HashMap<String, Integer>();
            for (ProductCategories productCategory : productCategories) {
                hashProductCategories.put(productCategory.getProductCategoryName(), productCategory.getProductCategoryId());
            }
            tx1.commit();
            session.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void viewProducts() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            Criteria c = session.createCriteria(Products.class);
            c.createAlias("productCategories", "pc");
            if (filterProductCategory.getProductCategoryId() != null) {
                c.add(Restrictions.eq("productCategories.productCategoryId", filterProductCategory.getProductCategoryId()));
            }
            c.addOrder(Order.asc("pc.productCategoryId"));
            products = c.list();
            tx1.commit();
            hashProducts = new HashMap<String, Integer>();
            for (Products product : products) {
                hashProducts.put(product.getProductName(), product.getProductsId());
            }
            session.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteFilter() {
        filterProductCategory = new ProductCategories();
        viewProducts();
    }

    public void insertProducts() {
        Boolean temp = false;
        try {
            for (Products product : products) {
                if (product.getProductName().toLowerCase().equals(selectedProduct.getProductName().toLowerCase())) {
                    temp = true;
                    break;
                }
            }
            if (temp.equals(false)) {
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                Integer id = (Integer) session.save(selectedProduct);
                tx1.commit();
                viewProducts();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successInsertMessage")));
                session.close();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("sameProductMessage"), ""));
            }
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedInsertMessage"), ""));
        }
    }

    public void updateProducts() {
        Boolean temp = false;
        try {
            for (Products product : products) {
                if (product.getProductName().toLowerCase().equals(selectedProduct.getProductName().toLowerCase()) && product.getProductsId() != selectedProduct.getProductsId()) {
                    temp = true;
                    break;
                }
            }
            if (temp.equals(false)) {
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                session.update(selectedProduct);
                tx1.commit();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
                session.close();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("sameProductMessage"), ""));
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
        }
        viewProducts();
    }

    public void deleteProducts(Products p) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            session.delete(p);
            products.remove(p);
            tx1.commit();
            session.close();
            viewProducts();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successDeleteMessage")));
            session.close();
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedDeleteMessage"), ""));

        }
    }

    public void selectedProductEmpty() {
        selectedProduct = new Products();
        selectedProduct.setProductCategories(new ProductCategories());
    }

    public void insertProductsCategory() {
        Boolean temp = false;
        try {
            for (ProductCategories pc : productCategories) {
                if (pc.getProductCategoryName().toLowerCase().equals(selectedProductsCategory.getProductCategoryName().toLowerCase())) {
                    temp = true;
                    break;
                }
            }
            if (temp.equals(false)) {
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                Integer id = (Integer) session.save(selectedProductsCategory);
                tx1.commit();
                viewProductCategories();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successInsertMessage")));
                session.close();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("sameProductCategoryMessage"), ""));
            }
        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedInsertMessage"), ""));
        }
    }

    public void updateProductsCategory() {
        Boolean temp = false;
        try {
            for (ProductCategories pc : productCategories) {
                if (pc.getProductCategoryName().toLowerCase().equals(selectedProductsCategory.getProductCategoryName().toLowerCase()) && pc.getProductCategoryId() != selectedProductsCategory.getProductCategoryId()) {
                    temp = true;
                    break;
                }
            }
            if (temp.equals(false)) {
                session = HibernateUtil.getSessionFactory().openSession();
                tx1 = session.beginTransaction();
                session.update(selectedProductsCategory);
                tx1.commit();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successUpdateMessage")));
                session.close();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("sameProductCategoryMessage"), ""));
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedUpdateMessage"), ""));
        }
        viewProductCategories();
        viewProducts();
    }

    public void deleteProductsCategory(ProductCategories p) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx1 = session.beginTransaction();
            ProductCategories tempProductCategories = (ProductCategories) session.get(ProductCategories.class, p.getProductCategoryId());
            Set<Products> productList = tempProductCategories.getProductses();
            System.out.println(productList.size());
            if (!productList.isEmpty()) {
               FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("fullProductCategoryMessage"), ""));
            } else {
                session.delete(tempProductCategories);
                tx1.commit();
                viewProductCategories();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage("successDeleteMessage")));
            }

        } catch (Exception e) {
            System.out.println(e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, OperationInternalizationMessage.operationMesssage("failedDeleteMessage"), ""));
        }
    }

    public void selectedProductCategoriesEmpty() {
        selectedProductsCategory = new ProductCategories();
    }

    public List<ProductCategories> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategories> productCategories) {
        this.productCategories = productCategories;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public Map<String, Integer> getHashProductCategories() {
        return hashProductCategories;
    }

    public void setHashProductCategories(Map<String, Integer> hashProductCategories) {
        this.hashProductCategories = hashProductCategories;
    }

    public ProductCategories getFilterProductCategory() {
        return filterProductCategory;
    }

    public void setFilterProductCategory(ProductCategories filterProductCategory) {
        this.filterProductCategory = filterProductCategory;
    }

    public Products getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Products selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public ProductCategories getSelectedProductsCategory() {
        return selectedProductsCategory;
    }

    public void setSelectedProductsCategory(ProductCategories selectedProductsCategory) {
        this.selectedProductsCategory = selectedProductsCategory;
    }

    public Map<String, Integer> getHashProducts() {
        return hashProducts;
    }

    public void setHashProducts(Map<String, Integer> hashProducts) {
        this.hashProducts = hashProducts;
    }

}
