package com.sustral.clientapi.controllers.helpers;

import com.sustral.clientapi.data.models.EmailVerificationEntity;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.EmailVerificationService;
import com.sustral.clientapi.dataservices.UserService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
import com.sustral.clientapi.miscservices.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * This helper class streamlines the process of setting a user's email when creating
 * a new account or changing the email on an existing account.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class UserEmailHelper {

    private final EmailVerificationService evService;
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public UserEmailHelper(EmailVerificationService evService, EmailService emailService, UserService userService) {
        this.evService = evService;
        this.emailService = emailService;
        this.userService = userService;
    }

    /**
     * Creates an emailVerificationEntity and sends the associated token to the user via email.
     *
     * @param user  the UserEntity of the user in question
     * @return      an int; 0 if successful, -1 if internal error, -2 if already verified
     */
    public int beginVerificationProcess(UserEntity user) {
        if (Boolean.TRUE.equals(user.getEmailVerified())) { return -2; }

        TokenWrapper<String, EmailVerificationEntity> token = evService.create(user.getId(), user.getEmail());
        if (token == null || token.getToken() == null) { return -1; }

        String encodedToken = Base64.getEncoder().encodeToString(token.getToken().getBytes());
        int emailSent = emailService.sendEmailVerificationEmail(user.getEmail(), user.getName(), encodedToken);
        if (emailSent < 0) { return -1; }

        return 0;
    }

    /**
     * Takes an email verification token and sets the associated user's verified email to true if the token is valid.
     *
     * The EmailVerificationService will only return a token if it is valid. The email stored in the returned
     * token is then compared to the current email of the user to ensure that the correct email is being verified.
     *
     * @param token a String; the unmodified token sent to the endpoint
     * @return      an int; 0 if successful, negative otherwise
     */
    public int finalizeVerification(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        EmailVerificationEntity verify = evService.getOneAndDeleteByToken(decodedToken);
        if (verify == null) { return -1; }

        UserEntity user = userService.getOneById(verify.getUserId());
        if (user == null) { return -1; }

        if (!verify.getEmail().equals(user.getEmail())) { return -1; }

        user = userService.setEmailVerifiedTrue(user);
        if (user == null) { return -1; }

        return 0;
    }

}
