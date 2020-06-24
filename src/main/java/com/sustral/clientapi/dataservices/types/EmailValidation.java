package com.sustral.clientapi.dataservices.types;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * Class used for validating user emails.
 *
 * @author Dilanka Dharmasena
 */
public class EmailValidation {
    @Email
    @Size(min = 5, max = 320)
    private final String email;

    public EmailValidation(String email) { this.email = email; }
}
