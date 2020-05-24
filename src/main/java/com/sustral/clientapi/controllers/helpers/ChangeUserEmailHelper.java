package com.sustral.clientapi.controllers.helpers;

import com.sustral.clientapi.data.models.EmailVerificationEntity;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.EmailVerificationService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
import com.sustral.clientapi.miscservices.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This helper class streamlines the process of setting a user's email when creating
 * a new account or changing the email on an existing account.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class ChangeUserEmailHelper {

    private final EmailVerificationService evService;
    private final EmailService emailService;

    @Autowired
    public ChangeUserEmailHelper(EmailVerificationService evService, EmailService emailService) {
        this.evService = evService;
        this.emailService = emailService;
    }

    /**
     * Creates an emailVerificationEntity and sends the associated token to the user via email.
     *
     * @param user  the UserEntity of the user in question
     * @return      an int; 0 if successful, negative otherwise
     */
    public int beginVerificationProcess(UserEntity user) {
        TokenWrapper<String, EmailVerificationEntity> token = evService.create(user.getId(), user.getEmail());
        if (token == null || token.getToken() == null) { return -1; }

        int emailSent = emailService.sendEmailVerificationEmail(user.getEmail(), user.getName(), token.getToken());
        if (emailSent < 0) { return -1; }

        return 0;
    }

}
