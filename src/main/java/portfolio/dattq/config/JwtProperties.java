package portfolio.dattq.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtProperties {
    private String jwtSecret;
    private int jwtExpirationMs;
    private long jwtRefreshExpirationMs;
} 