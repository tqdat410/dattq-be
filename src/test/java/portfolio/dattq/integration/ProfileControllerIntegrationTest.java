package portfolio.dattq.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import portfolio.dattq.payload.request.LoginRequest;
import portfolio.dattq.payload.request.ProfileRequest;
import portfolio.dattq.payload.response.JwtResponse;
import portfolio.dattq.util.TestDataInitializer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProfileControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private TestDataInitializer testDataInitializer;
    
    private String jwtToken;
    
    @BeforeEach
    public void setup() throws Exception {
        // Initialize test data
        testDataInitializer.initializeData();
        
        // Login to get token
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user");
        loginRequest.setPassword("password123");
        
        MvcResult result = mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn(); 
        
        String contentAsString = result.getResponse().getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(contentAsString, JwtResponse.class);
        jwtToken = "Bearer " + jwtResponse.getToken();
    }
    
    @Test
    public void shouldGetCurrentUserProfile() throws Exception {
        mockMvc.perform(get("/api/profile")
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.fullName").value("Regular User"));
    }
    
    @Test
    public void shouldGetProfileByUserId() throws Exception {
        // Get the profile of the admin user by ID (2)
        mockMvc.perform(get("/api/profile/2")
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("admin@example.com"))
                .andExpect(jsonPath("$.fullName").value("Admin User"));
    }
    
    @Test
    public void shouldUpdateProfile() throws Exception {
        // Create profile update request
        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.setFullName("Updated User Name");
        profileRequest.setBio("This is my updated bio");
        profileRequest.setCvUrl("https://example.com/updated-cv.pdf");
        
        // Perform update
        mockMvc.perform(put("/api/profile")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profileRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated User Name"))
                .andExpect(jsonPath("$.bio").value("This is my updated bio"))
                .andExpect(jsonPath("$.cvUrl").value("https://example.com/updated-cv.pdf"));
        
        // Verify the update was persisted
        mockMvc.perform(get("/api/profile")
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated User Name"));
    }
    
    @Test
    public void shouldRequireAuthenticationForProfile() throws Exception {
        // Attempt to access profile without token
        mockMvc.perform(get("/api/profile"))
                .andExpect(status().isUnauthorized());
    }
} 