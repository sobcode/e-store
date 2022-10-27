package com.finalVariant.OnlineStore.controller.command.userCommand.orderCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CancelRegisteredOrder implements Command {
    private final Logger logger = LogManager.getLogger(CancelRegisteredOrder.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        int orderId;
        String ordersPage = "redirect:/app/user/ordersPage";
        Order order;

        try{
            orderId = Integer.parseInt(request.getParameter("orderId"));
            order = userService.getOrderById(orderId).orElseThrow(FieldNotPresent::new);
        }catch (NumberFormatException | FieldNotPresent e){
            logger.warn(e.getMessage(), e);
            return ordersPage;
        }
        if(order.getStatus() == OrderStatus.Registered){
            userService.deleteOrder(order);
        }
        return ordersPage;
    }
}
