package com.finalVariant.OnlineStore.controller.command.adminCommand.orderCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;
import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OrdersManagePage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        /*HashMap<User, List<Order>> usersToOrders = new HashMap<>();
        List<User> users = adminService.getAllUsers();

        for(User user : users){
            usersToOrders.put(user, adminService.getAllOrdersForUser(user));
        }*/
        List<Order> usersToOrders = adminService.getAllOrders();

        request.setAttribute("usersToOrders", usersToOrders);
        return JSPPageConstants.MANAGE_ORDERS_PAGE;
    }
}
