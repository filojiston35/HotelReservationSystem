/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.components;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author deneme
 */
@ViewScoped
@ManagedBean
public class DateFormat implements Serializable {

    public String getMyFormattedDate(Date myDate) {
        if(myDate!=null)
        return new SimpleDateFormat("dd-MM-yyyy").format(myDate);
        else{
            return "";
        }
    }
    public String getMyFormattedDateLarge(Date myDate) {
        if(myDate!=null)
        return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(myDate);
        else{
            return "";
        }
    }
    public boolean twoDateComparison(Date startDate,Date endDate){
        if(endDate.before(startDate) || endDate.equals(startDate))
        {
            return false;
        }
        else{
            return true;
        }
    }
}
