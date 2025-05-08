package portfolio.dattq.payload.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ContactResponse {
    private Long id;
    private String type;
    private String label;
    private String url;
    private String icon;
    private LocalDateTime createdAt;
}
