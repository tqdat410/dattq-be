package portfolio.dattq.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import portfolio.dattq.model.RefreshToken;
import portfolio.dattq.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class RefreshTokenRepositoryTests {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindRefreshTokenByToken() {
        // Given
        User user = new User("testuser", "test@example.com", "password");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken("test-token");
        refreshToken.setExpiryDate(Instant.now().plusSeconds(3600));
        refreshTokenRepository.save(refreshToken);

        // When
        Optional<RefreshToken> found = refreshTokenRepository.findByToken("test-token");

        // Then
        assertTrue(found.isPresent());
        assertEquals(user.getId(), found.get().getUser().getId());
        assertEquals("test-token", found.get().getToken());
    }

    @Test
    public void shouldFindRefreshTokenByUser() {
        // Given
        User user = new User("testuser", "test@example.com", "password");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken("test-token");
        refreshToken.setExpiryDate(Instant.now().plusSeconds(3600));
        refreshTokenRepository.save(refreshToken);

        // When
        Optional<RefreshToken> found = refreshTokenRepository.findByUser(user);

        // Then
        assertTrue(found.isPresent());
        assertEquals("test-token", found.get().getToken());
    }

    @Test
    public void shouldDeleteRefreshTokenByUser() {
        // Given
        User user = new User("testuser", "test@example.com", "password");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken("test-token");
        refreshToken.setExpiryDate(Instant.now().plusSeconds(3600));
        refreshTokenRepository.save(refreshToken);

        // When
        int deletedCount = refreshTokenRepository.deleteByUser(user);

        // Then
        assertEquals(1, deletedCount);
        assertTrue(refreshTokenRepository.findByToken("test-token").isEmpty());
    }
} 