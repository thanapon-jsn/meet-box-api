package com.neah.meetbox.service;

import com.neah.meetbox.entity.UserProfile;
import com.neah.meetbox.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    private UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public Optional<UserProfile> getUserProfileById(Integer id) {
        return userProfileRepository.findById(id);
    }

    public UserProfile createUserProfile(UserProfile userProfile){
        UserProfile createUser = userProfileRepository.save(userProfile);

        return createUser == null ? new UserProfile() : createUser;
    }
}
