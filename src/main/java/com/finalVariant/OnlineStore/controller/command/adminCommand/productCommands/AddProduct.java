package com.finalVariant.OnlineStore.controller.command.adminCommand.productCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.util.CommandUtility;
import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AddProduct implements Command {
    private final Logger logger = LogManager.getLogger(AddProduct.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String productsManagePage = "redirect:/app/admin/productsManagePage";
        Product product;
        try{
            product = CommandUtility.extractProductFromForm(request, userService).orElseThrow(FieldNotPresent::new);
        }catch (FieldNotPresent e){
            logger.warn(e.getMessage(), e);
            return productsManagePage;
        }
        userService.createProduct(product);
        return productsManagePage;
    }
}
