package com.finalVariant.OnlineStore.controller.command.adminCommand.manageUserCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.model.dao.exception.FieldNotPresent;
import com.finalVariant.OnlineStore.model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateUserStatus implements Command {
    private final Logger logger = LogManager.getLogger(UpdateUserStatus.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String usersManagePage = "redirect:/app/admin/usersManagePage";
        try{
            int userId = Integer.parseInt(request.getParameter("userId"));
            User user = adminService.getUserById(userId).orElseThrow(FieldNotPresent::new);

            user.setStatus(User.UserStatus.valueOf(request.getParameter("status")));
            adminService.updateUser(user);
        }catch (NumberFormatException | FieldNotPresent e){
            logger.warn(e.getMessage(), e);
            return usersManagePage;
        }
        return usersManagePage;
    }
}
