package portfolio.dattq.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import portfolio.dattq.payload.request.LoginRequest;
import portfolio.dattq.payload.request.SignupRequest;
import portfolio.dattq.payload.request.TokenRefreshRequest;
import portfolio.dattq.payload.response.JwtResponse;
import portfolio.dattq.repository.UserRepository;
import portfolio.dattq.util.TestDataInitializer;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TestDataInitializer testDataInitializer;

    @Test
    public void shouldLoginSuccessfully() throws Exception {
        // Make sure test data is initialized
        testDataInitializer.initializeData();
        
        // Create login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("password123");
        
        // Perform login
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.refreshToken").exists())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }
    
    @Test
    public void shouldRegisterNewUser() throws Exception {
        // Make sure test data is initialized for roles
        testDataInitializer.initializeRoles();
        
        // Create signup request
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("newuser");
        signupRequest.setEmail("newuser@example.com");
        signupRequest.setPassword("newpassword123");
        Set<String> roles = new HashSet<>();
        roles.add("user");
        signupRequest.setRoles(roles);
        
        // Perform registration
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
        
        // Verify user was created
        assertTrue(userRepository.existsByUsername("newuser"));
        assertTrue(userRepository.existsByEmail("newuser@example.com"));
    }
    
    @Test
    public void shouldRefreshToken() throws Exception {
        // Make sure test data is initialized
        testDataInitializer.initializeData();
        
        // Login first to get tokens
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("password123");
        
        MvcResult result = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();
        
        // Extract the refresh token
        String contentAsString = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(contentAsString, JwtResponse.class);
        String refreshToken = jwtResponse.getRefreshToken();
        
        // Create refresh token request
        TokenRefreshRequest refreshRequest = new TokenRefreshRequest();
        refreshRequest.setRefreshToken(refreshToken);
        
        // Perform token refresh
        mockMvc.perform(post("/api/auth/refreshtoken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").value(refreshToken));
    }
    
    @Test
    public void shouldRejectInvalidLogin() throws Exception {
        // Create login request with invalid credentials
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("wrongpassword");
        
        // Perform login
        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
} 