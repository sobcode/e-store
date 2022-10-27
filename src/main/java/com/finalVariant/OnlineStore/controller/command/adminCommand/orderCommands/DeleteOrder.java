package com.finalVariant.OnlineStore.controller.command.adminCommand.orderCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DeleteOrder implements Command {
    private final Logger logger = LogManager.getLogger(DeleteOrder.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String ordersManagePage = "redirect:/app/admin/ordersManagePage";
        try{
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            userService.deleteOrder(adminService.getOrderById(orderId).orElseThrow(FieldNotPresent::new));
        }catch (NumberFormatException | FieldNotPresent e){
            logger.warn(e.getMessage(), e);
            return ordersManagePage;
        }
        return ordersManagePage;
    }
}
