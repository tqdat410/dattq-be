package portfolio.dattq.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthEntryPointJwtTests {

    @Mock
    private HttpServletRequest request;

    @Mock
    private AuthenticationException authException;

    @InjectMocks
    private AuthEntryPointJwt authEntryPointJwt;

    @Test
    public void shouldCommenceWithUnauthorizedError() throws IOException, ServletException {
        // Given
        MockHttpServletResponse response = new MockHttpServletResponse();
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        
        when(request.getServletPath()).thenReturn("/api/test");
        when(authException.getMessage()).thenReturn("Unauthorized");
        when(response.getWriter()).thenReturn(writer);
        
        // When
        authEntryPointJwt.commence(request, response, authException);
        
        // Then
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        
        String responseBody = stringWriter.toString();
        assertTrue(responseBody.contains("\"status\":401"));
        assertTrue(responseBody.contains("\"error\":\"Unauthorized\""));
        assertTrue(responseBody.contains("\"message\":\"Unauthorized\""));
        assertTrue(responseBody.contains("\"path\":\"/api/test\""));
    }
} 