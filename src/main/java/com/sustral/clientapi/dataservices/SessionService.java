package com.sustral.clientapi.dataservices;

import com.sustral.clientapi.data.models.SessionEntity;
import com.sustral.clientapi.dataservices.types.TokenWrapper;

/**
 * Defines Session CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface SessionService {
    // Query Methods

    /**
     * Given a plaintext token, this methods finds a valid token(if it exists), then deletes it.
     *
     * This method checks if the token has expired.
     *
     * @param token a string token that was sent by the user
     * @return      a SessionEntity; null if not found or error
     */
    SessionEntity findOneAndDeleteByToken(String token);

    // Mutation Methods

    /**
     * Creates a session(refresh) token and a database representation of it.
     *
     * Does not create the corresponding JWT.
     *
     * @param userId    a string userId of the user whose token this is
     * @return          a TokenWrapper containing the String token and SessionEntity
     */
    TokenWrapper<String, SessionEntity> create(String userId);

    // Utilities

}
