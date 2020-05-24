package com.sustral.clientapi.dataservices;

import com.sustral.clientapi.data.models.UserEntity;

import java.util.List;

/**
 * Defines User CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface UserService {
    // Query Methods

    /**
     * Returns a user given an id.
     *
     * @param id    a string user id
     * @return      a UserEntity, null if not found or error
     */
    UserEntity getOneById(String id);

    /**
     * Returns a list of users given a list of ids.
     *
     * @param ids   a List of string ids
     * @return      a list of UserEntities
     */
    List<UserEntity> getManyById(List<String> ids);

    /**
     * Returns a user given a valid email.
     *
     * @param email a string email
     * @return      a UserEntity, null if not found or error
     */
    UserEntity getOneByEmail(String email);

    // Mutation Methods

    /**
     * Creates a new user.
     *
     * Ensures that name, email, and password meet standards.
     * Ensures that no current user has the same email.
     * Encrypts the password prior to storage.
     *
     * @param name      a string name; something the user goes by
     * @param email     a string email
     * @param password  a string password
     * @return          a UserEntity
     */
    UserEntity create(String name, String email, String password);

    /**
     * Changes the name of the given user.
     *
     * Ensures that the name is within length limits and contains valid characters.
     *
     * @param user  a UserEntity
     * @param name  a string name
     * @return      a UserEntity
     */
    UserEntity setName(UserEntity user, String name);

    /**
     * Changes the email of the given user.
     *
     * Ensures that no other user has the same email.
     * Ensures that this is a valid email within length limits.
     * Sets emailVerified to false, but it will not send an email or create a verification token.
     *
     * @param user  a UserEntity
     * @param email a string email
     * @return      a UserEntity
     */
    UserEntity setEmail(UserEntity user, String email);

    /**
     * Changes the password of the given user.
     *
     * Ensures that the password meets requirements.
     * Encrypts the password prior to storage.
     *
     * @param user      a UserEntity
     * @param password  a string password
     * @return          a UserEntity
     */
    UserEntity setPassword(UserEntity user, String password);

    /**
     * Changes the status of emailVerified to true for the given user.
     *
     * @param user  a UserEntity
     * @return      a UserEntity
     */
    UserEntity setEmailVerifiedTrue(UserEntity user);

    // Utility Methods

    /**
     * Returns whether the given user has the given password.
     *
     * Encrypts the given password prior to comparison.
     *
     * @param user      a UserEntity usually acquired from getUserByEmail
     * @param password  a string password submitted by the user
     * @return          a boolean
     */
    boolean validateAuth(UserEntity user, String password);
}
