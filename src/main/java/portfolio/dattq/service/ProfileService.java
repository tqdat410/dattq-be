package portfolio.dattq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.dattq.model.Profile;
import portfolio.dattq.model.User;
import portfolio.dattq.payload.request.ProfileRequest;
import portfolio.dattq.payload.response.MessageResponse;
import portfolio.dattq.payload.response.ProfileResponse;
import portfolio.dattq.payload.response.CvurlResponse;
import portfolio.dattq.repository.ProfileRepository;
import portfolio.dattq.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<ProfileResponse> getCurrentUserProfile(Long userId) {
        Optional<Profile> profileOptional = profileRepository.findByUserId(userId);
        if (profileOptional.isPresent()) {
            return Optional.of(new ProfileResponse(profileOptional.get()));
        } else {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                Profile newProfile = new Profile(userOptional.get());
                profileRepository.save(newProfile);
                return Optional.of(new ProfileResponse(newProfile));
            }
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public Optional<ProfileResponse> getUserProfile(Long userId) {
        Optional<Profile> profileOptional = profileRepository.findByUserId(userId);
        return profileOptional.map(ProfileResponse::new);
    }

    @Transactional
    public Object updateProfile(Long userId, ProfileRequest profileRequest) {
        Optional<Profile> profileOptional = profileRepository.findByUserId(userId);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setFullName(profileRequest.getFullName());
            profile.setAvatarUrl(profileRequest.getAvatarUrl());
            profile.setBio(profileRequest.getBio());
            profile.setUpdatedAt(LocalDateTime.now());
            profileRepository.save(profile);
            return new ProfileResponse(profile);
        } else {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                Profile newProfile = new Profile(userOptional.get());
                newProfile.setFullName(profileRequest.getFullName());
                newProfile.setAvatarUrl(profileRequest.getAvatarUrl());
                newProfile.setBio(profileRequest.getBio());
                newProfile.setCvUrl(profileRequest.getCvUrl());
                newProfile.setUpdatedAt(LocalDateTime.now());
                profileRepository.save(newProfile);
                return new ProfileResponse(newProfile);
            }
            return new MessageResponse("Error: User not found!");
        }
    }

    public CvurlResponse getCvurlOfUser1() {
        Optional<Profile> profileOptional = profileRepository.findByUserId(1L);
        if (profileOptional.isPresent()) {
            return new CvurlResponse(profileOptional.get().getCvUrl());
        } else {
            return new CvurlResponse(null);
        }
    }

    @Transactional
    public CvurlResponse updateCvurlOfUser1(String cvurl) {
        Optional<Profile> profileOptional = profileRepository.findByUserId(1L);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setCvUrl(cvurl);
            profile.setUpdatedAt(LocalDateTime.now());
            profileRepository.save(profile);
            return new CvurlResponse(profile.getCvUrl());
        } else {
            return new CvurlResponse(null);
        }
    }
}
