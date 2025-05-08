package portfolio.dattq.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import portfolio.dattq.model.Profile;
import portfolio.dattq.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProfileRepositoryTests {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindProfileByUser() {
        // Given
        User user = new User("testuser", "test@example.com", "password");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        Profile profile = new Profile(user);
        profile.setFullName("Test User");
        profileRepository.save(profile);

        // When
        Optional<Profile> found = profileRepository.findByUser(user);

        // Then
        assertTrue(found.isPresent());
        assertEquals("Test User", found.get().getFullName());
        assertEquals(user.getId(), found.get().getUser().getId());
    }

    @Test
    public void shouldFindProfileByUserId() {
        // Given
        User user = new User("testuser", "test@example.com", "password");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        Profile profile = new Profile(user);
        profile.setFullName("Test User");
        profileRepository.save(profile);

        // When
        Optional<Profile> found = profileRepository.findByUserId(user.getId());

        // Then
        assertTrue(found.isPresent());
        assertEquals("Test User", found.get().getFullName());
        assertEquals(user.getId(), found.get().getUser().getId());
    }

    @Test
    public void shouldNotFindProfileForNonExistentUser() {
        // Given
        // No user or profile saved

        // When
        Optional<Profile> found = profileRepository.findByUserId(999L);

        // Then
        assertFalse(found.isPresent());
    }
} 