package portfolio.dattq.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
    private String fullName;
    private String avatarUrl;
    private String bio;
    private String cvUrl;
} 