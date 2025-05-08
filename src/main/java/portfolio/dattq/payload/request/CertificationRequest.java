package portfolio.dattq.payload.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CertificationRequest {
    private String title;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String credentialId;
    private String credentialUrl;
    private String description;
}
