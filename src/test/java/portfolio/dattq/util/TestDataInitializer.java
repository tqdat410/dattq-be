package portfolio.dattq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility class for initializing test data programmatically.
 * Can be used as an alternative to SQL scripts. For SQL version of test data,
 * see the "Sample Test Data" section in database.md
 */
@TestComponent
public class TestDataInitializer {

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
     * Initialize all test data
     */
    @Transactional
    public void initializeData() {
        // Initialize roles
        initializeRoles();
        
        // Initialize users with roles
        initializeUsers();
        
        // Initialize profiles
        initializeProfiles();
        
        // Initialize refresh tokens
        initializeRefreshTokens();
    }

    /**
     * Initialize role data
     */
    @Transactional
    public void initializeRoles() {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        
        roleRepository.save(userRole);
        roleRepository.save(adminRole);
    }

    /**
     * Initialize user data
     */
    @Transactional
    public void initializeUsers() {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Create regular user
        User user = new User("user", "user@example.com", passwordEncoder.encode("password123"));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        userRepository.save(user);

        // Create admin user
        User admin = new User("admin", "admin@example.com", passwordEncoder.encode("admin123"));
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(userRole);
        adminRoles.add(adminRole);
        admin.setRoles(adminRoles);
        userRepository.save(admin);

        // Create test user
        User testUser = new User("testuser", "test@example.com", passwordEncoder.encode("testuser"));
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());
        Set<Role> testUserRoles = new HashSet<>();
        testUserRoles.add(userRole);
        testUser.setRoles(testUserRoles);
        userRepository.save(testUser);
    }

    /**
     * Initialize profile data
     */
    @Transactional
    public void initializeProfiles() {
        // Get users
        User user = userRepository.findByUsername("user").orElseThrow();
        User admin = userRepository.findByUsername("admin").orElseThrow();
        User testUser = userRepository.findByUsername("testuser").orElseThrow();

        // Create user profile
        Profile userProfile = new Profile(user);
        userProfile.setFullName("Regular User");
        userProfile.setAvatarUrl("https://example.com/avatars/user.jpg");
        userProfile.setBio("I am a regular user");
        profileRepository.save(userProfile);

        // Create admin profile
        Profile adminProfile = new Profile(admin);
        adminProfile.setFullName("Admin User");
        adminProfile.setAvatarUrl("https://example.com/avatars/admin.jpg");
        adminProfile.setBio("I am an admin user with special privileges");
        profileRepository.save(adminProfile);

        // Create test user profile
        Profile testProfile = new Profile(testUser);
        testProfile.setFullName("Test User");
        testProfile.setAvatarUrl("https://example.com/avatars/test.jpg");
        testProfile.setBio("This is a test account");
        profileRepository.save(testProfile);
    }

    /**
     * Initialize refresh token data
     */
    @Transactional
    public void initializeRefreshTokens() {
        // Get users
        User user = userRepository.findByUsername("user").orElseThrow();
        User admin = userRepository.findByUsername("admin").orElseThrow();
        User testUser = userRepository.findByUsername("testuser").orElseThrow();

        // Create refresh token for user
        RefreshToken userToken = new RefreshToken();
        userToken.setUser(user);
        userToken.setToken("user-refresh-token-sample");
        userToken.setExpiryDate(Instant.now().plusSeconds(604800)); // 7 days
        refreshTokenRepository.save(userToken);

        // Create refresh token for admin
        RefreshToken adminToken = new RefreshToken();
        adminToken.setUser(admin);
        adminToken.setToken("admin-refresh-token-sample");
        adminToken.setExpiryDate(Instant.now().plusSeconds(604800)); // 7 days
        refreshTokenRepository.save(adminToken);

        // Create refresh token for test user
        RefreshToken testToken = new RefreshToken();
        testToken.setUser(testUser);
        testToken.setToken("test-refresh-token-sample");
        testToken.setExpiryDate(Instant.now().plusSeconds(604800)); // 7 days
        refreshTokenRepository.save(testToken);
    }
} 