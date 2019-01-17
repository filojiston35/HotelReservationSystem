package com.hotelrezervation.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@SessionScoped
public class LanguageController implements Serializable {

    private String language;

    @PostConstruct
    public void init() {

    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void actionTurkce() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("tr"));
        language = "tr";
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(LanguageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actionEnglish() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("en"));
        language = "en";
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(LanguageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
