/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.core;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author deneme
 */
public class OperationInternalizationMessage implements Serializable {

    public static String operationMesssage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        String message = bundle.getString(key);
        return message;
    }

    public static String operationMesssage1Param(String key, String param1) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        String message = MessageFormat.format(bundle.getString(key), param1);
        return message;
    }

    public static String operationMesssage2Param(String key, String param1,String param2) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        String message = MessageFormat.format(bundle.getString(key), param1,param2);
        return message;
    }

    public static String operationMesssage3Param(String key, String param1,String param2,String param3) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        String message = MessageFormat.format(bundle.getString(key), param1,param2,param3);
        return message;
    }
}
