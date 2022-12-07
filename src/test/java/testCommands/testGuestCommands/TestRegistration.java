package testCommands.testGuestCommands;

import com.finalVariant.OnlineStore.controller.command.guestCommand.Registration;
import com.finalVariant.OnlineStore.controller.constants.JSPPageConstants;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class TestRegistration {
    private final Registration registration = new Registration();
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
            Assertions.assertEquals(JSPPageConstants.REGISTRATION_PAGE, registration.execute(httpRequest));
        } catch (ServletException | IOException e) {
            Assertions.fail("Error: " + e);
        }
    }

    @Test
    public void testNullPassword(){
        when(httpRequest.getParameter("password")).thenReturn(null);

        try {
            Assertions.assertEquals(JSPPageConstants.REGISTRATION_PAGE, registration.execute(httpRequest));
        } catch (ServletException | IOException e) {
            Assertions.fail("Error: " + e);
        }
    }

    @Test
    public void testInvalidLoginOrPassword(){
        when(httpRequest.getParameter("login")).thenReturn("123");
        when(httpRequest.getParameter("password")).thenReturn("123");
        when(httpRequest.getSession()).thenReturn(httpSession);

        try {
            assertEquals("redirect:/app/guest/registration", registration.execute(httpRequest));
        } catch (ServletException | IOException e) {
            fail();
        }
    }
}
