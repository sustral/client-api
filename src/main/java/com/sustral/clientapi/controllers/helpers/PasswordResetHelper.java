package com.sustral.clientapi.controllers.helpers;

import com.sustral.clientapi.data.models.PasswordResetEntity;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.PasswordResetService;
import com.sustral.clientapi.dataservices.UserService;
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
public class PasswordResetHelper {

    private final PasswordResetService passwordResetService;
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public PasswordResetHelper(PasswordResetService passwordResetService, EmailService emailService, UserService userService) {
        this.passwordResetService = passwordResetService;
        this.emailService = emailService;
        this.userService = userService;
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

    /**
     * Given a valid password reset token, the associated user's password is changed to the given password.
     *
     * The PasswordResetService will only return a token if it has been deemed valid. In addition, the UserService
     * will only set the password if it meets requirements.
     *
     * @param token     a String; the unmodified token sent by the user
     * @param password  a String; the password to be set
     * @return          an int; 0 is successful, negative otherwise
     */
    public int finalizePasswordReset(String token, String password) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        PasswordResetEntity reset = passwordResetService.findOneAndDeleteByToken(decodedToken);
        if (reset == null) { return -1; }

        UserEntity user = userService.getOneById(reset.getUserId());
        if (user == null) { return -1; }

        user = userService.setPassword(user, password);
        if (user == null) { return -1; }

        return 0;
    }

}
