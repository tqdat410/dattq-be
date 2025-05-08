package portfolio.dattq.payload.response;

import lombok.Getter;
import lombok.Setter;
import portfolio.dattq.model.Profile;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileResponse {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String bio;
    private String cvUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public ProfileResponse(Profile profile) {
        this.id = profile.getId();
        this.userId = profile.getUser().getId();
        this.username = profile.getUser().getUsername();
        this.email = profile.getUser().getEmail();
        this.fullName = profile.getFullName();
        this.avatarUrl = profile.getAvatarUrl();
        this.bio = profile.getBio();
        this.cvUrl = profile.getCvUrl();
        this.createdAt = profile.getCreatedAt();
        this.updatedAt = profile.getUpdatedAt();
    }
} 