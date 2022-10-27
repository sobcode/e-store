package com.finalVariant.OnlineStore.controller.command.guestCommand;

import com.finalVariant.OnlineStore.controller.command.Command;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;
import com.finalVariant.OnlineStore.controller.util.CommandUtility;
import com.finalVariant.OnlineStore.model.entity.Order;
import com.finalVariant.OnlineStore.model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Login implements Command {
    private final Logger logger = LogManager.getLogger(Login.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if(login == null || password == null){
            return JSPPageConstants.LOGIN_PAGE;
        }

        User user = User.createUser(login, password, User.Role.User, User.UserStatus.Unblocked);
        if(userService.DBContainUser(user)){
            if(!"Blocked".equals(user.getStatus().toString())) {
                if (CommandUtility.checkUserIsLogged(session, user)) {
                    session.setAttribute("error", true);
                    session.setAttribute("errorType", "user_already_logged");
                    logger.info("trying to log for already logged user session id: " + request.getSession().getId());
                    return "redirect:/app/guest/login";
                }
            }

            session.setAttribute("user", user);

            if(session.getAttribute("cart") != null && user.getRole() != User.Role.Admin){
                userService.retainCartForLoggedUser((List<Order>)session.getAttribute("cart"), user);
                session.removeAttribute("cart");
            }
            return "redirect:/app/" + user.getRole().toString().toLowerCase(Locale.ROOT) + "/mainPage";
        }

        session.setAttribute("error", true);
        session.setAttribute("errorType", "wrong_data");
        logger.info("wrong data in login or password, session id: " + request.getSession().getId());
        return "redirect:/app/guest/login";
    }
}
