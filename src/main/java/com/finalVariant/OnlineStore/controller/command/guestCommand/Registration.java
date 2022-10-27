package com.finalVariant.OnlineStore.controller.command.guestCommand;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;
import com.finalVariant.OnlineStore.controller.util.ValidationUtil;
import com.finalVariant.OnlineStore.model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration implements Command {
    private final Logger logger = LogManager.getLogger(Registration.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        if(login == null || password == null){
            return JSPPageConstants.REGISTRATION_PAGE;
        }

        String registrationPage = "redirect:/app/guest/registration";

        if(!ValidationUtil.isLoginValid(login) || !ValidationUtil.isPasswordValid(password)){
            request.setAttribute("error", "invalid data");
            logger.info("wrong data in registration or password, session id: " + request.getSession().getId());
            return registrationPage;
        }

        if(userService.createNewUser(User.createUser(login, password, User.Role.User, User.UserStatus.Unblocked))){
            return "redirect:/app/guest/login";
        }

        logger.info("login already exist, session id: " + request.getSession().getId());
        session.setAttribute("loginAlreadyExist", "true");
        return registrationPage;
    }
}
