package com.finalVariant.OnlineStore.controller.command.adminCommand.manageUserCommands;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UsersManagePage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        request.setAttribute("users", userService.getAllUsers());
        return JSPPageConstants.MANAGE_USERS_PAGE;
    }
}
