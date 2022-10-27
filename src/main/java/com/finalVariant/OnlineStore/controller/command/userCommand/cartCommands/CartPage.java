package com.finalVariant.OnlineStore.controller.command.userCommand.cartCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;
import com.finalVariant.OnlineStore.model.entity.User;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") == null ? null : (User) session.getAttribute("user");

        if(user != null){
            request.setAttribute("cart", userService.getOrdersByStatus(user, OrderStatus.Unregistered));
        }
        return JSPPageConstants.CART_PAGE;
    }
}
