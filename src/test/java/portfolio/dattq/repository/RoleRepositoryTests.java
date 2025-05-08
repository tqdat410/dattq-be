package portfolio.dattq.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import portfolio.dattq.model.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void shouldFindRoleByName() {
        // Given
        Role role = new Role("ROLE_TEST");
        roleRepository.save(role);

        // When
        Optional<Role> found = roleRepository.findByName("ROLE_TEST");

        // Then
        assertTrue(found.isPresent());
        assertEquals("ROLE_TEST", found.get().getName());
    }

    @Test
    public void shouldNotFindNonExistentRole() {
        // Given
        Role role = new Role("ROLE_TEST");
        roleRepository.save(role);

        // When
        Optional<Role> found = roleRepository.findByName("ROLE_NONEXISTENT");

        // Then
        assertFalse(found.isPresent());
    }
} 