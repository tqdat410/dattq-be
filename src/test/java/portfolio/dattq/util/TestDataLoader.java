package portfolio.dattq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import portfolio.dattq.model.Profile;
import portfolio.dattq.model.RefreshToken;
import portfolio.dattq.model.Role;
import portfolio.dattq.model.User;
import portfolio.dattq.repository.ProfileRepository;
import portfolio.dattq.repository.RefreshTokenRepository;
import portfolio.dattq.repository.RoleRepository;
import portfolio.dattq.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * Utility class for loading test data programmatically
 * Alternative to using SQL scripts. For SQL version of test data,
 * see the "Sample Test Data" section in database.md
 */
@Component
public class TestDataLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creates sample test data for the application
     */
    @Transactional
    public void loadTestData() {
        // Create roles
        Role userRole = createRoleIfNotFound("ROLE_USER");
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");

        // Create users
        User admin = createUser("admin", "admin@example.com", "password123");
        admin.getRoles().add(userRole);
        admin.getRoles().add(adminRole);
        userRepository.save(admin);

        User user1 = createUser("user1", "user1@example.com", "password123");
        user1.getRoles().add(userRole);
        userRepository.save(user1);

        User user2 = createUser("user2", "user2@example.com", "password123");
        user2.getRoles().add(userRole);
        userRepository.save(user2);

        // Create profiles
        createProfile(admin, "Admin User", "https://example.com/avatars/admin.jpg",
                "Administrator of the website", "+1234567890",
                "123 Admin St, Admin City", LocalDate.of(1990, 1, 1));

        createProfile(user1, "Test User One", "https://example.com/avatars/user1.jpg",
                "This is a test user with a sample bio", "+1987654321",
                "456 User St, User City", LocalDate.of(1992, 5, 15));

        createProfile(user2, "Test User Two", "https://example.com/avatars/user2.jpg",
                "Another test user with sample information", "+1122334455",
                "789 Sample Ave, Test Town", LocalDate.of(1988, 11, 30));

        // Create refresh tokens
        createRefreshToken(admin, "admin-refresh-token-12345", 7);
        createRefreshToken(user1, "user1-refresh-token-67890", 7);
        createRefreshToken(user2, "user2-refresh-token-abcde", 7);
    }

    private Role createRoleIfNotFound(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(name);
                    return roleRepository.save(role);
                });
    }

    private User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRoles(new HashSet<>());
        return user;
    }

    private Profile createProfile(User user, String fullName, String avatarUrl, String bio,
                               String phoneNumber, String address, LocalDate birthDate) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setFullName(fullName);
        profile.setAvatarUrl(avatarUrl);
        profile.setBio(bio);
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());
        return profileRepository.save(profile);
    }

    private RefreshToken createRefreshToken(User user, String token, int daysValid) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(daysValid * 86400L)); // days to seconds
        refreshToken.setCreatedAt(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }
} 