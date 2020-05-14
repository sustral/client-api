package com.sustral.clientapi.services;

import com.sustral.clientapi.data.models.PasswordResetEntity;
import com.sustral.clientapi.services.types.ServiceReturn;
import com.sustral.clientapi.services.types.TokenWrapper;

/**
 * Defines PasswordReset CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface PasswordResetService {
    // Query Methods

    /**
     * Returns an active PasswordResetEntity if the given token matches.
     *
     * The entity will then be removed from the database so that it cannot be used again.
     *
     * @param token a string token sent in by the user
     * @return      a ServiceReturn containing the PasswordResetEntity
     */
    ServiceReturn<PasswordResetEntity> findOneAndDeleteByToken(String token);

    // Mutation Methods

    /**
     * Creates a PasswordReset for the given user.
     *
     * Will not send the email to the user.
     *
     * @param userId    a string userId
     * @return          a ServiceReturn containing a TokenWrapper
     */
    ServiceReturn<TokenWrapper<String, PasswordResetEntity>> create(String userId);

    // Utilities

}
