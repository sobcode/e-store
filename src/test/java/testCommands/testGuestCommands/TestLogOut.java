package testCommands.testGuestCommands;

import com.finalVariant.OnlineStore.controller.command.guestCommand.LogOut;
import com.finalVariant.OnlineStore.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TestLogOut {
    private final LogOut logOut = new LogOut();
    private final HttpServletRequest httpRequest = mock(HttpServletRequest.class);
    private final HttpSession session = mock(HttpSession.class);

    @AfterEach
    public void resetMocks() {
        reset(session, httpRequest);
    }

    @Test
    void testLogOut() {
        User user = User.createUser("TestLogin", "TestPassword", User.Role.User, User.UserStatus.Unblocked);
        HashSet<String> loggedUsers = new HashSet<>();
        loggedUsers.add("TestUser");
        ServletContext context = mock(ServletContext.class);

        when(httpRequest.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(session.getServletContext()).thenReturn(context);
        when(context.getAttribute("loggedUsers")).thenReturn(loggedUsers);
        assertEquals("redirect:/app/guest/mainPage", logOut.execute(httpRequest));
    }
}
