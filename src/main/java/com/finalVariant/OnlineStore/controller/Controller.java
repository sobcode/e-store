package com.finalVariant.OnlineStore.controller;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.command.adminCommand.manageUserCommands.UpdateUserStatus;
import com.finalVariant.OnlineStore.controller.command.adminCommand.manageUserCommands.UsersManagePage;
import com.finalVariant.OnlineStore.controller.command.adminCommand.orderCommands.DeleteOrder;
import com.finalVariant.OnlineStore.controller.command.adminCommand.orderCommands.OrdersManagePage;
import com.finalVariant.OnlineStore.controller.command.adminCommand.orderCommands.UpdateOrderStatus;
import com.finalVariant.OnlineStore.controller.command.adminCommand.productCommands.AddProduct;
import com.finalVariant.OnlineStore.controller.command.adminCommand.productCommands.DeleteProduct;
import com.finalVariant.OnlineStore.controller.command.adminCommand.productCommands.ProductsManagePage;
import com.finalVariant.OnlineStore.controller.command.adminCommand.productCommands.UpdateProduct;
import com.finalVariant.OnlineStore.controller.command.guestCommand.LogOut;
import com.finalVariant.OnlineStore.controller.command.guestCommand.Login;
import com.finalVariant.OnlineStore.controller.command.guestCommand.Registration;
import com.finalVariant.OnlineStore.controller.command.userCommand.MainPage;
import com.finalVariant.OnlineStore.controller.command.userCommand.cartCommands.AddToCart;
import com.finalVariant.OnlineStore.controller.command.userCommand.cartCommands.BuyFromCart;
import com.finalVariant.OnlineStore.controller.command.userCommand.cartCommands.CartPage;
import com.finalVariant.OnlineStore.controller.command.userCommand.cartCommands.ChangeProductQuantity;
import com.finalVariant.OnlineStore.controller.command.userCommand.orderCommands.CancelRegisteredOrder;
import com.finalVariant.OnlineStore.controller.command.userCommand.orderCommands.OrdersPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/")
public class Controller extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    @Override
    public void init() {
        commands.put("registration", new Registration());
        commands.put("login", new Login());
        commands.put("mainPage", new MainPage());
        commands.put("logout", new LogOut());
        commands.put("addToCart", new AddToCart());
        commands.put("cartPage", new CartPage());
        commands.put("changeProductQuantity", new ChangeProductQuantity());
        commands.put("buyFromCart", new BuyFromCart());
        commands.put("ordersPage", new OrdersPage());
        commands.put("cancelRegisteredOrder", new CancelRegisteredOrder());
        commands.put("addProduct", new AddProduct());
        commands.put("productsManagePage", new ProductsManagePage());
        commands.put("deleteProduct", new DeleteProduct());
        commands.put("updateOrderStatus", new UpdateOrderStatus());
        commands.put("ordersManagePage", new OrdersManagePage());
        commands.put("deleteOrder", new DeleteOrder());
        commands.put("updateProduct", new UpdateProduct());
        commands.put("usersManagePage", new UsersManagePage());
        commands.put("updateUserStatus", new UpdateUserStatus());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String path = req.getRequestURI();
        /*if(path.contains("product-image")){
            resp.sendRedirect(path);
            return;
        }*/

        path = path.replaceAll(".*/app/.*/", "");

        Command command = commands.getOrDefault(path, r -> "mainPage");
        String page = command.execute(req);

        if(page.contains("redirect:")){
            resp.sendRedirect(page.replace("redirect:", ""));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }
}
