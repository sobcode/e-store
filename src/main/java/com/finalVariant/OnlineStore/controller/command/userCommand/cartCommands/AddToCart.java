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

public class AddToCart implements Command {
    private final Logger logger = LogManager.getLogger(AddToCart.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        int productId;
        HttpSession session = request.getSession();

        User user = session.getAttribute("user") == null ? null : (User) session.getAttribute("user");
        User.Role role = user == null ? User.Role.Guest : user.getRole();
        String mainPage = "redirect:/app/" + role.toString().toLowerCase(Locale.ROOT) + "/mainPage";
        Product product;

        try{
            productId = Integer.parseInt(request.getParameter("productId"));
            product = userService.getProductById(productId);
        }catch (NumberFormatException | FieldNotPresent e){
            logger.error(e.getMessage(), e);
            return mainPage;
        }

        if(user == null){
            CommandUtility.addProductToCartForUnloggedUser(session, product, 1);
        } else {
            Order order = Order.createOrder(user, product, 1, OrderStatus.Unregistered);
            if(!userService.createOrder(order)){
                List<Order> orders = userService.getOrdersByStatus(user, OrderStatus.Unregistered);
                try{
                    order = orders.stream()
                            .filter(e -> e.getProduct().getId() == productId)
                            .findFirst().orElseThrow(FieldNotPresent::new);
                } catch (FieldNotPresent e) {
                    logger.error(e.getMessage(), e);
                    return mainPage;
                }
                userService.incrementOrderQuantity(order);
            }
        }
        return mainPage;
    }
}
