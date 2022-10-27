package com.finalVariant.OnlineStore.controller.filter;

import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;
import com.finalVariant.OnlineStore.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
        User user = httpRequest.getSession().getAttribute("user") == null ? null
                  : (User)httpRequest.getSession().getAttribute("user");

        if(user != null){
            if(user.getStatus().equals(User.UserStatus.Blocked) && !httpRequest.getRequestURI().contains("logout")){
                httpRequest.getSession().removeAttribute("user");
                httpRequest.getRequestDispatcher(JSPPageConstants.BLOCK_ERROR).forward(httpRequest, httpResponse);
                return;
            }
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }
}
