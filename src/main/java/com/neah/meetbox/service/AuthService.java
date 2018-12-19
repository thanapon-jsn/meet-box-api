package com.neah.meetbox.service;

import com.neah.meetbox.entity.UserProfile;
import com.neah.meetbox.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserProfileRepository userProfileRepository;

    @Autowired
    public AuthService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public Optional<UserProfile> signIn(String username, String password) {

        Optional<UserProfile> userProfile = userProfileRepository.findAllByUsername(username);

        if (!userProfile.isPresent()) {
            return Optional.empty();
        }

        if (!userProfile.get().getPassword().equals(password)) {
            return Optional.empty();
        }

        return userProfile;
    }

    public UserProfile signUp(UserProfile userProfile) {
        UserProfile createUser = userProfileRepository.save(userProfile);

        return createUser == null ? new UserProfile() : createUser;
    }
}
