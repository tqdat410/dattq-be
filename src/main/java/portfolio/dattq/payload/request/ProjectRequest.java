package portfolio.dattq.payload.request;

import lombok.Data;

@Data
public class ProjectRequest {
    private String title;
    private String description;
    private String techStack;
    private String githubUrl;
    private String liveUrl;
    private String imageUrl;
    private String projectType;
}
