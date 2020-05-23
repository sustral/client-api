package com.sustral.clientapi.utils.email;

/**
 * A set of methods that enables a caller to send users email.
 *
 * Each method corresponds to an email that a user will or may need to receive (eg. password reset, email verification).
 *
 * @author Dilanka Dharmasena
 */
public interface EmailService {

    /**
     * Sends a new user the welcome email.
     *
     * @param email     a string; the user's email address
     * @param name      a string; the user's name
     * @return          an int; 0 if successful, negative otherwise
     */
    int sendWelcomeEmail(String email, String name);

    /**
     * Sends a user an email verification email.
     *
     * @param email     a string; the user's email address
     * @param name      a string; the user's name
     * @param token     a string; an email verification token
     * @return          an int; 0 if successful, negative otherwise
     */
    int sendEmailVerificationEmail(String email, String name, String token);

    /**
     * Sends a user a password reset email.
     *
     * @param email     a string; the user's email address
     * @param name      a string; the user's name
     * @param token     a string; a password reset token
     * @return          an int; 0 if successful, negative otherwise
     */
    int sendPasswordResetEmail(String email, String name, String token);

}
