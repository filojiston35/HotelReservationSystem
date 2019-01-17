/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.components;

import com.hotelrezervation.core.OperationInternalizationMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author deneme
 */
public class StatisticsProperties {

    public static List<MonthProperties> monthList;
    public static List<MonthProperties> totalAmountList;
    
    public static List<MonthProperties> monthList() {
        monthList = new ArrayList<MonthProperties>();
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("OCAK"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("ŞUBAT"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("MART"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("NİSAN"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("MAYIS"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("HAZİRAN"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("TEMMUZ"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("AĞUSTOS"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("EYLÜL"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("EKİM"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("KASIM"), 0, 0));
        monthList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("ARALIK"), 0, 0));
        return monthList;
    }
    public static List<MonthProperties> totalAmountList() {
        totalAmountList = new ArrayList<MonthProperties>();
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("OCAK"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("ŞUBAT"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("MART"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("NİSAN"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("MAYIS"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("HAZİRAN"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("TEMMUZ"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("AĞUSTOS"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("EYLÜL"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("EKİM"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("KASIM"), 0, 0));
        totalAmountList.add(new MonthProperties(OperationInternalizationMessage.operationMesssage("ARALIK"), 0, 0));
        return totalAmountList;
    }


}
