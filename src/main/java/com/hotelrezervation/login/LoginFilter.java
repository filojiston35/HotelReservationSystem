/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotelrezervation.login;

import com.hotelrezervation.model.Personnel;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author deneme
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getRequestURI();
        if (url == "/hotel-rezervation-1.0/") {
            resp.sendRedirect(req.getContextPath() + "/login.xhtml");
        } else {
            Personnel personnel = (Personnel) req.getSession().getAttribute("user");
            String token = req.getParameter("token");
            if (personnel == null) {
                if (url.contains("secured/passwordChange.xhtml") && token != null) {
                    chain.doFilter(request, response);
                } else if (url.contains("secured") || url.contains("logout")) {
                    resp.sendRedirect(req.getContextPath() + "/login.xhtml");
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                if (url.contains("login")) {
                    resp.sendRedirect(req.getContextPath() + "/secured/home.xhtml");
                } else if (url.contains("logout")) {
                    req.getSession().invalidate();
                    resp.sendRedirect(req.getContextPath() + "/login.xhtml");
                } else {
                    chain.doFilter(request, response);
                }

            }
        }

    }

    @Override
    public void destroy() {

    }

}
