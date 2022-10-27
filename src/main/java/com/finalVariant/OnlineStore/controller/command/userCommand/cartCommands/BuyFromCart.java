package com.finalVariant.OnlineStore.controller.command.userCommand.cartCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.User;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BuyFromCart implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") == null ? null : (User) session.getAttribute("user");
        User.Role role = user == null ? User.Role.Guest : user.getRole();

        String cartPage = "redirect:/app/" + role.toString().toLowerCase(Locale.ROOT) + "/cartPage";
        String loginPage = "redirect:/app/guest/login";

        if(user == null){
            return loginPage;
        }

        List<Order> orders = userService.getOrdersByStatus(user, OrderStatus.Unregistered);
        if(orders.size() > 0){
            userService.updateStatusForOrders(orders, OrderStatus.Registered);
        }
        return cartPage;
    }
}
