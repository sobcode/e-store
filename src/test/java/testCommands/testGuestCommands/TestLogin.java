package testCommands.testGuestCommands;

import com.finalVariant.OnlineStore.controller.command.guestCommand.Login;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;
import com.finalVariant.OnlineStore.model.dao.DaoFactory;
import com.finalVariant.OnlineStore.model.dao.UserDao;
import com.finalVariant.OnlineStore.model.entity.User;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;


public class TestLogin {
    private final Login login = new Login();
    private final HttpServletRequest httpRequest = mock(HttpServletRequest.class);
    private final HttpSession httpSession = mock(HttpSession.class);

    @After
    public void resetMocks(){
        reset(httpSession, httpRequest);
    }

    @Test
    public void testNullLogin(){
        when(httpRequest.getParameter("login")).thenReturn(null);

        try {
            Assertions.assertEquals(JSPPageConstants.LOGIN_PAGE, login.execute(httpRequest));
        } catch (ServletException | IOException e) {
            Assertions.fail("Exception: " + e);
        }
    }

    @Test
    public void testNullPassword(){
        when(httpRequest.getParameter("login")).thenReturn("Login");
        when(httpRequest.getParameter("password")).thenReturn(null);

        try {
            Assertions.assertEquals(JSPPageConstants.LOGIN_PAGE, login.execute(httpRequest));
        } catch (ServletException | IOException e) {
            Assertions.fail("Exception: " + e);
        }
    }

    //@Test
    public void testValidLogin(){
        String userLogin = "TestLogin";
        String userPassword = "TestPassword";
        User user = User.createUser(0, userLogin, userPassword, User.Role.User, User.UserStatus.Unblocked);
        try(UserDao userDao = mock(UserDao.class)) {
            DaoFactory daoFactory = mock(DaoFactory.class);
            when(daoFactory.createUserDao()).thenReturn(userDao);
            ServletContext servletContext = mock(ServletContext.class);
            when(userDao.findByLogin(userLogin)).thenReturn(Optional.of(user));
            when(httpRequest.getParameter("login")).thenReturn(userLogin);
            when(httpRequest.getParameter("password")).thenReturn(userPassword);
            when(httpRequest.getSession()).thenReturn(httpSession);
            when(httpSession.getServletContext()).thenReturn(servletContext);
            when(servletContext.getAttribute("loggedUsers")).thenReturn(new HashSet<>());
            when(httpSession.getAttribute("cart")).thenReturn(null);
        }
        try {
            Assertions.assertEquals("redirect:/app/user/mainPage", login.execute(httpRequest));
        } catch (ServletException | IOException e) {
            Assertions.fail("Exception: " + e);
        }
    }

    @Test
    public void testUserAlreadyLogged(){
        ServletContext context = mock(ServletContext.class);
        HashSet<String> loggedUsers = new HashSet<>();
        loggedUsers.add("TestUser");

        when(httpRequest.getParameter("login")).thenReturn("TestLogin");
        when(httpRequest.getParameter("password")).thenReturn("TestPassword");
        when(httpRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);

        try {
            Assertions.assertEquals("redirect:/app/guest/login", login.execute(httpRequest));
        } catch (ServletException | IOException e){
            Assertions.fail("Exception: " + e);
        }
    }
}
