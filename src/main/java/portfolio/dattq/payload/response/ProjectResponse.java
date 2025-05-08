package portfolio.dattq.payload.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private String techStack;
    private String githubUrl;
    private String liveUrl;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String projectType;
}
