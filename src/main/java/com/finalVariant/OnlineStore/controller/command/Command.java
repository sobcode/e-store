package com.finalVariant.OnlineStore.controller.command;

import com.finalVariant.OnlineStore.model.service.impl.AdminService;
import com.finalVariant.OnlineStore.model.service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Command {
    UserService userService = UserService.getInstance();
    AdminService adminService = AdminService.getInstance();

    String execute(HttpServletRequest request) throws ServletException, IOException;
}
