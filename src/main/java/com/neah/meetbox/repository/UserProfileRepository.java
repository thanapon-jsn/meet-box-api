package com.neah.meetbox.repository;

import com.neah.meetbox.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {

    Optional<UserProfile> findAllByUsername(String username);
}
