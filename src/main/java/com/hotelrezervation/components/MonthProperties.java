/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.components;

/**
 *
 * @author deneme
 */
public class MonthProperties {

    private String monthName;
    private Integer totalDay;
    private Double totalPrice;

    public MonthProperties(String monthName, Integer totalDay,double totalPrice) {
        this.monthName = monthName;
        this.totalDay = totalDay;
        this.totalPrice=totalPrice;
    }

    public MonthProperties() {
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

   
    
}
