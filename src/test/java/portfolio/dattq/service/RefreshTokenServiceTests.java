package portfolio.dattq.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import portfolio.dattq.exception.TokenRefreshException;
import portfolio.dattq.model.RefreshToken;
import portfolio.dattq.model.User;
import portfolio.dattq.repository.RefreshTokenRepository;
import portfolio.dattq.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTests {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(refreshTokenService, "refreshTokenDurationMs", 86400000L);
    }

    @Test
    public void shouldCreateRefreshToken() {
        // Given
        User user = new User();
        user.setId(1L);
        
        RefreshToken savedRefreshToken = new RefreshToken();
        savedRefreshToken.setUser(user);
        savedRefreshToken.setExpiryDate(Instant.now().plusMillis(86400000L));
        savedRefreshToken.setToken("test-token");
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(savedRefreshToken);
        
        // When
        RefreshToken result = refreshTokenService.createRefreshToken(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals("test-token", result.getToken());
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    public void shouldVerifyExpirationOfValidToken() {
        // Given
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusSeconds(3600));
        
        // When
        RefreshToken result = refreshTokenService.verifyExpiration(refreshToken);
        
        // Then
        assertEquals(refreshToken, result);
    }

    @Test
    public void shouldThrowExceptionForExpiredToken() {
        // Given
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("expired-token");
        refreshToken.setExpiryDate(Instant.now().minusSeconds(3600));
        
        // When/Then
        TokenRefreshException exception = assertThrows(TokenRefreshException.class, () -> {
            refreshTokenService.verifyExpiration(refreshToken);
        });
        
        assertTrue(exception.getMessage().contains("Refresh token was expired"));
        verify(refreshTokenRepository, times(1)).delete(refreshToken);
    }

    @Test
    public void shouldFindTokenByTokenString() {
        // Given
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("test-token");
        
        when(refreshTokenRepository.findByToken("test-token")).thenReturn(Optional.of(refreshToken));
        
        // When
        Optional<RefreshToken> result = refreshTokenService.findByToken("test-token");
        
        // Then
        assertTrue(result.isPresent());
        assertEquals("test-token", result.get().getToken());
    }

    @Test
    public void shouldDeleteByUserId() {
        // Given
        User user = new User();
        user.setId(1L);
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(refreshTokenRepository.deleteByUser(user)).thenReturn(1);
        
        // When
        int result = refreshTokenService.deleteByUserId(1L);
        
        // Then
        assertEquals(1, result);
        verify(refreshTokenRepository, times(1)).deleteByUser(user);
    }
} 