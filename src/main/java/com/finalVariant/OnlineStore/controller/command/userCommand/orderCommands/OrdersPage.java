package com.finalVariant.OnlineStore.controller.command.userCommand.orderCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;
import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.User;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrdersPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") == null ? null : (User) session.getAttribute("user");
        List<Order> orders = new ArrayList<>();
        for(OrderStatus orderStatus : OrderStatus.values()){
            if(orderStatus != OrderStatus.Unregistered) {
                orders.addAll(userService.getOrdersByStatus(user, orderStatus));
            }
        }
        request.setAttribute("orders", orders);
        return JSPPageConstants.ORDERS_PAGE;
    }
}
