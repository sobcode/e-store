package com.finalVariant.OnlineStore.controller.command.adminCommand.productCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ProductsManagePage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        request.setAttribute("categories", userService.getAllCategories());
        request.setAttribute("colors", userService.getAllColors());
        request.setAttribute("sizes", userService.getAllSizes());
        request.setAttribute("products", userService.getAllProducts());
        return JSPPageConstants.MANAGE_PRODUCTS_PAGE;
    }
}
