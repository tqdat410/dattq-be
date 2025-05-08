package portfolio.dattq.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "tech_stack", length = 255)
    private String techStack;

    @Column(name = "github_url", length = 255)
    private String githubUrl;

    @Column(name = "live_url", length = 255)
    private String liveUrl;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Builder.Default
    @Column(name = "project_type", length = 20)
    private String projectType = "Personal";

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
