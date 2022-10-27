package com.finalVariant.OnlineStore.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LocalizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        String lang = servletRequest.getParameter("lang");

        if(lang != null){
            httpRequest.getSession().setAttribute("lang", lang);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
