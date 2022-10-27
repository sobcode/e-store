package com.finalVariant.OnlineStore.controller.command.adminCommand.productCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateProduct implements Command {
    private final Logger logger = LogManager.getLogger(UpdateProduct.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String productsManagePage = "redirect:/app/admin/productsManagePage";
        try{
            int productId = Integer.parseInt(request.getParameter("productId"));
            int categoryId = Integer.parseInt(request.getParameter("category"));
            int price = Integer.parseInt(request.getParameter("price"));
            int sizeId = Integer.parseInt(request.getParameter("size"));
            int colorId = Integer.parseInt(request.getParameter("color"));

            Product product = userService.getProductById(productId);
            product.setName(request.getParameter("name"));
            product.setCategory(userService.getCategoryById(categoryId));
            product.setPrice(price);
            product.setSize(userService.getSizeByID(sizeId));
            product.setColor(userService.getColorByID(colorId));
            product.setSex(Product.Sex.valueOf(request.getParameter("sex")));
            adminService.updateProduct(product);
        } catch (NumberFormatException | FieldNotPresent e) {
            logger.warn(e.getMessage(), e);
            return productsManagePage;
        }
        return productsManagePage;
    }
}
