package com.finalVariant.OnlineStore.controller.command.guestCommand;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.util.CommandUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LogOut implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        if(request.getSession().getAttribute("user") != null){
            CommandUtility.deleteUserFromLogged(request.getSession());
        }
        return "redirect:/app/guest/mainPage";
    }
}
