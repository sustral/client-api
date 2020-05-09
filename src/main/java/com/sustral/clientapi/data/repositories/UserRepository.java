package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    /**
     * Gets a user by their email
     *
     * @param email a prevalidated string containing the user's email
     * @return      the requested user or null if they are not found
     */
    UserEntity findByEmail(String email);
}
