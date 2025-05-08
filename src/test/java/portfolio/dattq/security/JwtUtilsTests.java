package portfolio.dattq.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import portfolio.dattq.config.JwtProperties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTests {

    @Mock
    private JwtProperties jwtProperties;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setup() {
        // Set up test JWT secret and expiration
        when(jwtProperties.getJwtSecret()).thenReturn("testSecretKeyWithMinimum256BitsLengthForHS256Algorithm");
        when(jwtProperties.getJwtExpirationMs()).thenReturn(60000); // 1 minute
        
        // Mock authentication principal
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");
    }

    @Test
    public void shouldGenerateJwtToken() {
        // When
        String token = jwtUtils.generateJwtToken(authentication);
        
        // Then
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void shouldGenerateTokenFromUsername() {
        // When
        String token = jwtUtils.generateTokenFromUsername("testuser");
        
        // Then
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void shouldValidateValidToken() {
        // Given
        String token = jwtUtils.generateTokenFromUsername("testuser");
        
        // When
        boolean isValid = jwtUtils.validateJwtToken(token);
        
        // Then
        assertTrue(isValid);
    }

    @Test
    public void shouldGetUsernameFromToken() {
        // Given
        String token = jwtUtils.generateTokenFromUsername("testuser");
        
        // When
        String username = jwtUtils.getUserNameFromJwtToken(token);
        
        // Then
        assertEquals("testuser", username);
    }
} 