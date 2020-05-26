package com.sustral.clientapi.controllers.helpers;

import com.sustral.clientapi.data.models.PasswordResetEntity;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.PasswordResetService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
import com.sustral.clientapi.miscservices.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * This class streamlines the issuing of password reset requests.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class RequestPasswordResetHelper {

    private final PasswordResetService passwordResetService;
    private final EmailService emailService;

    @Autowired
    public RequestPasswordResetHelper(PasswordResetService passwordResetService, EmailService emailService) {
        this.passwordResetService = passwordResetService;
        this.emailService = emailService;
    }

    /**
     * This method creates a password reset token and sends that token to the given user via email.
     *
     * @param user  a UserEntity representing the user who requested the reset
     * @return      an int; 0 if successful, negative otherwise
     */
    public int beginPasswordResetProcess(UserEntity user) {

        TokenWrapper <String, PasswordResetEntity> passwordResetWrapper = passwordResetService.create(user.getId());
        if (passwordResetWrapper == null || passwordResetWrapper.getToken() == null) { return -1; }

        String encodedToken = Base64.getEncoder().encodeToString(passwordResetWrapper.getToken().getBytes());

        int emailSent = emailService.sendPasswordResetEmail(user.getEmail(), user.getName(), encodedToken);
        if (emailSent < 0) { return -1; }

        return 0;
    }

}
