package portfolio.dattq.mapper;

import org.mapstruct.Mapper;
import portfolio.dattq.model.Profile;
import portfolio.dattq.payload.response.ProfileResponse;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileResponse toProfileResponse(Profile profile);
    // Add more mapping methods as needed
}
