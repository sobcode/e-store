package com.finalVariant.OnlineStore.controller.customTags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PriceTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String currency = "$";
        if(request.getSession().getAttribute("lang") != null && request.getSession().getAttribute("lang").equals("ukr")){
            currency = "\u20B4";
        }
        try{
            out.print(currency);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
}
