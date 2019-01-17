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
public class StaticChartObject {

    private Integer year;
    private Integer value;

    public StaticChartObject(Integer year, Integer value) {
        this.year = year;
        this.value = value;
    }

    public StaticChartObject() {
    }
    

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
