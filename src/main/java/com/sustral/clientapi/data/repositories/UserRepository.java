package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Dilanka Dharmasena
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
    /**
     * Gets a user by their email.
     *
     * @param email a prevalidated string containing the user's email
     * @return      the requested user or null if they are not found
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Returns whether a user with the given email exists.
     *
     * @param email a string email
     * @return      a boolean; true if the user exists, false otherwise
     */
    boolean existsByEmail(String email);
}
