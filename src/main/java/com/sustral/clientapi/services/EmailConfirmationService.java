package com.sustral.clientapi.services;

import com.sustral.clientapi.data.models.EmailConfirmationEntity;
import com.sustral.clientapi.services.types.ServiceReturn;
import com.sustral.clientapi.services.types.TokenWrapper;

/**
 * Defines EmailConfirmation CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface EmailConfirmationService {
    // Query Methods

    /**
     * Returns an EmailConfirmationEntity if one exists that is both unexpired and has the matching token.
     *
     * The Entity will then be deleted to prevent reuse.
     *
     * @param token a string token sent in by the user
     * @return      a ServiceReturn containing an EmailConfirmationEntity
     */
    ServiceReturn<EmailConfirmationEntity> getOneAndDeleteByToken(String token);

    // Mutation Methods

    /**
     * Creates an EmailConfirmationEntity and a token to send to the user.
     *
     * This method does not send an email to the user.
     *
     * @param userId    a string userId
     * @param email     the email to be confirmed
     * @return          a ServiceReturn containing a TokenWrapper
     */
    ServiceReturn<TokenWrapper<String, EmailConfirmationEntity>> create(String userId, String email);

    // Utilities

}
