package portfolio.dattq.payload.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CertificationResponse {
    private Long id;
    private String title;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String credentialId;
    private String credentialUrl;
    private String description;
    private LocalDateTime createdAt;
}
