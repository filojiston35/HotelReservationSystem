package com.hotelrezervation.managment;

import com.hotelrezervation.core.HibernateUtil;
import com.hotelrezervation.components.StatisticsProperties;
import com.hotelrezervation.components.MonthProperties;
import com.hotelrezervation.components.StaticChartObject;
import com.hotelrezervation.core.OperationInternalizationMessage;
import com.hotelrezervation.model.Reservation;
import com.hotelrezervation.model.Rooms;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;

@ManagedBean
@ViewScoped
public class StatisticsOperations implements Serializable {

    private BarChartModel barModel1;
    private BarChartModel barModel2;
    private LineChartModel lineModel1;
    private LineChartModel lineModel2;
    private List<Integer> dateList;
    private Integer selectedYear;
    private Integer lastFiveYear;
    private Session session;
    private Transaction tx1;
    private List<Reservation> reservationList;
    private List<Rooms> roomList;
    private List<Integer> chart1DataList;
    private List<Integer> line1DataList;
    private List<StaticChartObject> chart2DataList;
    private List<StaticChartObject> line2DataList;
    private Calendar nowYear;
    private Boolean selectedYearControl = true;
    private List<MonthProperties> monthList;
    private List<MonthProperties> totalAmountList;

    @PostConstruct
    public void init() {
        Date now = new Date();
        nowYear = Calendar.getInstance();
        nowYear.setTime(now);
        selectedYear = nowYear.get(Calendar.YEAR);
        loadDateList();
        loadReservationAndRoomList();
        createBarModelForSelectedYear();
        createLineModelForSelectedYear();

    }

    public void loadReservationAndRoomList() {
        session = HibernateUtil.getSessionFactory().openSession();
        tx1 = session.beginTransaction();
        reservationList = session.createCriteria(Reservation.class).list();
        session.close();

    }

    public void loadDateList() {
        dateList = new ArrayList<Integer>();
        Date temp = new Date();
        for (int i = nowYear.get(Calendar.YEAR) + 1; i >= nowYear.get(Calendar.YEAR) - 20; i--) {
            dateList.add(i);
        }
    }

    public void itemSelect(ItemSelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedYearControl.equals(true)) {
            String param1 = Integer.toString(selectedYear);
            String param2 = monthList.get(event.getItemIndex()).getMonthName();
            String param3 = Integer.toString(chart1DataList.get(event.getItemIndex()));
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage3Param("itemSelectMessage1", param1, param2, param3)));
        } else {
            String param1 = Integer.toString(chart2DataList.get(event.getItemIndex()).getYear());
            String param2 = Integer.toString(chart2DataList.get(event.getItemIndex()).getValue());
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage2Param("itemSelectMessage2", param1, param2)));
        }

    }
    
     public void lineItemSelect(ItemSelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedYearControl.equals(true)) {
            String param1 = Integer.toString(selectedYear);
            String param2 = totalAmountList.get(event.getItemIndex()).getMonthName();
            String param3 = Integer.toString(line1DataList.get(event.getItemIndex()));
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage3Param("itemLineSelectMessage1", param1, param2, param3)));
        } else {
            String param1 = Integer.toString(line2DataList.get(event.getItemIndex()).getYear());
            String param2 = Integer.toString(line2DataList.get(event.getItemIndex()).getValue());
            context.addMessage(null, new FacesMessage(OperationInternalizationMessage.operationMesssage2Param("itemLineSelectMessage2", param1, param2)));
        }

    }

    public void viewSelectedDate() {
        createBarModelForSelectedYear();
        createLineModelForSelectedYear();
        selectedYearControl = true;
    }

    public void viewLastFiveYear() {
        createBarModelLastFiveYear();
        createLineModelLastFiveYear();

        selectedYearControl = false;
    }

    private BarChartModel initBarModelForSelectedYear() {

        BarChartModel model = new BarChartModel();
        ChartSeries A = new ChartSeries();
        A.setLabel(OperationInternalizationMessage.operationMesssage("lastFiveYearLabel"));
        int temp = 0;

        //baslangıc tarihi
        Calendar resStartDate = Calendar.getInstance();
        //bitis tarihi
        Calendar resEndDate = Calendar.getInstance();
        //ay listesi çekiliyor
        monthList = StatisticsProperties.monthList();
        chart1DataList = new ArrayList<Integer>();

        for (Reservation reservation : reservationList) {
            resStartDate.setTime(reservation.getReservationStartDate());    //Res baslangıc tarihi
            resEndDate.setTime(reservation.getReservationEndDate());        //Res bitis tarihi

            while (resStartDate.getTimeInMillis() != resEndDate.getTimeInMillis()) {    //baslangıc bitişe eşit olana kadar döndür.
                if (resStartDate.get(Calendar.YEAR) == selectedYear) {      //Bu arada tarihi de kontrol et yeni yıla girmesin.
                    // monthlist içindeki ayların value değerleri müşterinin  kaldığı hergün için +1 oluyor.
                    MonthProperties tempMonth = monthList.get(resStartDate.get(Calendar.MONTH));
                    tempMonth.setTotalDay(tempMonth.getTotalDay() + 1);
                    resStartDate.setTimeInMillis(resStartDate.getTimeInMillis() + 86400000);
                } else {
                    resStartDate.setTimeInMillis(resStartDate.getTimeInMillis() + 86400000);
                }
            }
        }
        //monthlist içindeki değerler döndürülür. A nın içine basılıyor.
        for (MonthProperties m : monthList) {
            A.set(m.getMonthName(), m.getTotalDay());
            chart1DataList.add(m.getTotalDay());
        }
        model.addSeries(A);
        return model;
    }

    private void createBarModelForSelectedYear() {

        barModel1 = initBarModelForSelectedYear();

        barModel1.setTitle(OperationInternalizationMessage.operationMesssage1Param("selectedYearTitle", Integer.toString(selectedYear)));
        barModel1.setLegendPosition("ne");

        Axis xAxis = barModel1.getAxis(AxisType.X);
        xAxis.setLabel(OperationInternalizationMessage.operationMesssage("selectedYearX"));

        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setLabel(OperationInternalizationMessage.operationMesssage("lastFiveYearY"));
        yAxis.setMin(0);

    }

    private BarChartModel initBarModelLastFiveYear() {
        BarChartModel model = new BarChartModel();
        ChartSeries A = new ChartSeries();
        A.setLabel(OperationInternalizationMessage.operationMesssage("lastFiveYearLabel"));
        int temp = 0;
        Calendar resStartDate = Calendar.getInstance();
        Calendar resEndDate = Calendar.getInstance();
        chart2DataList = new ArrayList<StaticChartObject>();
        for (int i = nowYear.get(Calendar.YEAR) - 5; i <= nowYear.get(Calendar.YEAR) + 1; i++) {
            temp = 0;
            for (Reservation reservation : reservationList) {
                resStartDate.setTime(reservation.getReservationStartDate());
                resEndDate.setTime(reservation.getReservationEndDate());
                while (resStartDate.getTimeInMillis() != resEndDate.getTimeInMillis()) {
                    if (resStartDate.get(Calendar.YEAR) == i) {
                        temp += 1;
                        resStartDate.setTimeInMillis(resStartDate.getTimeInMillis() + 86400000);
                    } else {
                        resStartDate.setTimeInMillis(resStartDate.getTimeInMillis() + 86400000);
                    }
                }

            }
            A.set(i, temp);
            StaticChartObject sco = new StaticChartObject(i, temp);
            chart2DataList.add(sco);
        }
        model.addSeries(A);
        return model;
    }

    private void createBarModelLastFiveYear() {

        barModel1 = initBarModelLastFiveYear();

        barModel1.setTitle(OperationInternalizationMessage.operationMesssage("lastFiveYearTitle"));
        barModel1.setLegendPosition("ne");

        Axis xAxis = barModel1.getAxis(AxisType.X);
        xAxis.setLabel(OperationInternalizationMessage.operationMesssage("lastFiveYearX"));

        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setLabel(OperationInternalizationMessage.operationMesssage("lastFiveYearY"));
        yAxis.setMin(0);
    }

    private LineChartModel initLineModelSelectedYear() {

        LineChartModel model = new LineChartModel();
        ChartSeries A = new ChartSeries();
         A.setLabel(OperationInternalizationMessage.operationMesssage("lineLastFiveYearY"));
        int temp = 0;
        Calendar resStartDate = Calendar.getInstance();
        Calendar resEndDate = Calendar.getInstance();
        double resPrice;
        int diff = 0;
        totalAmountList = StatisticsProperties.totalAmountList();
        line1DataList=new ArrayList<Integer>();
        for (Reservation reservation : reservationList) {
            resStartDate.setTime(reservation.getReservationStartDate());
            resEndDate.setTime(reservation.getReservationEndDate());
            resPrice = reservation.getReservationPrice();
            diff = (int) Math.abs((resEndDate.getTimeInMillis() - resStartDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
            if (resPrice != 0) {
                while (resStartDate.getTimeInMillis() != resEndDate.getTimeInMillis()) {
                    if (resStartDate.get(Calendar.YEAR) == selectedYear) {
                        MonthProperties tempMonth = totalAmountList.get(resStartDate.get(Calendar.MONTH));
                        tempMonth.setTotalPrice(tempMonth.getTotalPrice() + (resPrice / diff));
                        resStartDate.setTimeInMillis(resStartDate.getTimeInMillis() + 86400000);
                    } else {
                        resStartDate.setTimeInMillis(resStartDate.getTimeInMillis() + 86400000);
                    }
                }
            }

        }
        //totalAmountList içindeki veriler döndürülüp A nın içine basılacak.
        for (MonthProperties monthProperties : totalAmountList) {
            A.set(monthProperties.getMonthName(), monthProperties.getTotalPrice());
            line1DataList.add(monthProperties.getTotalPrice().intValue());
        }
        model.addSeries(A);
        return model;

    }

    private void createLineModelForSelectedYear() {
        lineModel1 = initLineModelSelectedYear();
        lineModel1.setTitle(OperationInternalizationMessage.operationMesssage1Param("selectedYearTitle", Integer.toString(selectedYear)));
        lineModel1.setLegendPosition("ne");
        lineModel1.setShowPointLabels(true);
        lineModel1.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        Axis xAxis = lineModel1.getAxis(AxisType.X);
        xAxis.setLabel(OperationInternalizationMessage.operationMesssage("selectedYearX"));
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setLabel(OperationInternalizationMessage.operationMesssage("lineLastFiveYearY"));
        yAxis.setMin(0);
    }

    private LineChartModel initLineModelLastFveYear() {

        LineChartModel model = new LineChartModel();
        ChartSeries A = new ChartSeries();
        A.setLabel("Toplam kazanç");
        int temp = 0;
        Calendar resStartDate = Calendar.getInstance();
        Calendar resEndDate = Calendar.getInstance();
        line2DataList = new ArrayList<StaticChartObject>();
        double resPrice;
        int diff = 0;

        for (int i = nowYear.get(Calendar.YEAR) - 5; i <= nowYear.get(Calendar.YEAR) + 1; i++) {
            temp = 0;
            for (Reservation reservation : reservationList) {
                resStartDate.setTime(reservation.getReservationStartDate());
                resEndDate.setTime(reservation.getReservationEndDate());
                resPrice = reservation.getReservationPrice();
                diff = (int) Math.abs((resEndDate.getTimeInMillis() - resStartDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
                if (resPrice != 0) {
                    while (resStartDate.getTimeInMillis() != resEndDate.getTimeInMillis()) {
                        if (resStartDate.get(Calendar.YEAR) == i) {
                            temp += resPrice / diff;
                            resStartDate.setTimeInMillis(resStartDate.getTimeInMillis() + 86400000);
                        } else {
                            resStartDate.setTimeInMillis(resStartDate.getTimeInMillis() + 86400000);
                        }
                    }
                }

            }
            A.set(i, temp);
            StaticChartObject sco = new StaticChartObject(i, temp);
            line2DataList.add(sco);
        }
        model.addSeries(A);
        return model;

    }

    private void createLineModelLastFiveYear() {
        lineModel1 = initLineModelLastFveYear();
        lineModel1.setTitle(OperationInternalizationMessage.operationMesssage("lastFiveYearTitle"));
        lineModel1.setLegendPosition("ne");
        lineModel1.setShowPointLabels(true);
        lineModel1.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        Axis xAxis = lineModel1.getAxis(AxisType.X);
        xAxis.setLabel(OperationInternalizationMessage.operationMesssage("lastFiveYearX"));
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setLabel(OperationInternalizationMessage.operationMesssage("lineLastFiveYearY"));
        yAxis.setMin(0);
    }

    public BarChartModel getBarModel1() {
        return barModel1;
    }

    public void setBarModel1(BarChartModel barModel1) {
        this.barModel1 = barModel1;
    }

    public BarChartModel getBarModel2() {
        return barModel2;
    }

    public void setBarModel2(BarChartModel barModel2) {
        this.barModel2 = barModel2;
    }

    public List<Integer> getDateList() {
        return dateList;
    }

    public void setDateList(List<Integer> dateList) {
        this.dateList = dateList;
    }

    public Integer getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(Integer selectedYear) {
        this.selectedYear = selectedYear;
    }

    public Integer getLastFiveYear() {
        return lastFiveYear;
    }

    public void setLastFiveYear(Integer lastFiveYear) {
        this.lastFiveYear = lastFiveYear;
    }

    public LineChartModel getLineModel1() {
        return lineModel1;
    }

    public void setLineModel1(LineChartModel lineModel1) {
        this.lineModel1 = lineModel1;
    }

}
