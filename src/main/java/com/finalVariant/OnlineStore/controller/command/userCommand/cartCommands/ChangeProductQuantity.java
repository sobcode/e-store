package com.finalVariant.OnlineStore.controller.command.userCommand.cartCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.util.CommandUtility;
import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.Product;
import com.finalVariant.OnlineStore.model.entity.User;
import com.finalVariant.OnlineStore.model.entity.emums.OrderStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ChangeProductQuantity implements Command {
    private final Logger logger = LogManager.getLogger(ChangeProductQuantity.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        int productId;
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;
        User.Role role = user == null ? User.Role.Guest : user.getRole();
        String cartPage = "redirect:/app/" + role.toString().toLowerCase(Locale.ROOT) + "/cartPage";
        Product product;

        try{
            productId = Integer.parseInt(request.getParameter("productId"));
            product = userService.getProductById(productId);
        }catch (NumberFormatException | FieldNotPresent e) {
            logger.warn(e.getMessage(), e);
            return cartPage;
        }

        if(user == null){
            if(action.equals("increment")){
                CommandUtility.addProductToCartForUnloggedUser(session, product, 1);
            }
            if(action.equals("decrement")){
                CommandUtility.addProductToCartForUnloggedUser(session, product, -1);
            }
            if(action.equals("remove")){
                CommandUtility.removeProductFromCartForUnloggedUser(session, product);
            }
        } else {
            List<Order> orders = userService.getOrdersByStatus(user, OrderStatus.Unregistered);
            Order order;
            try{
                order = orders.stream()
                        .filter(e -> e.getProduct().getId() == productId)
                        .findFirst().orElseThrow(FieldNotPresent::new);
            } catch (FieldNotPresent e) {
                logger.warn(e.getMessage(), e);
                return cartPage;
            }

            if(action.equals("increment")){
                userService.incrementOrderQuantity(order);
            }
            if(action.equals("decrement")){
                userService.decrementOrderQuantity(order);
            }
            if(action.equals("remove")){
                userService.deleteOrder(order);
            }
        }
        return cartPage;
    }
}
