package portfolio.dattq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class JwtConfig {
    
    private final Environment env;
    
    public JwtConfig(Environment env) {
        this.env = env;
    }
    
    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties properties = new JwtProperties();
        
        // Set a default if property is not found
        String jwtSecret = env.getProperty("dattq.app.jwtSecret", "defaultJwtSecretKeyForTokenSigningThatShouldBeLongEnough");
        properties.setJwtSecret(jwtSecret);
        
        // Default to 24 hours (in milliseconds)
        int jwtExpirationMs = env.getProperty("dattq.app.jwtExpirationMs", Integer.class, 86400000);
        properties.setJwtExpirationMs(jwtExpirationMs);
        
        // Default to 7 days (in milliseconds)
        long jwtRefreshExpirationMs = env.getProperty("dattq.app.jwtRefreshExpirationMs", Long.class, 604800000L);
        properties.setJwtRefreshExpirationMs(jwtRefreshExpirationMs);
        
        return properties;
    }
} 