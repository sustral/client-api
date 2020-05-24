package com.sustral.clientapi.dataservices.types;

import javax.validation.constraints.Pattern;

/**
 * Class used for validating user passwords.
 *
 * @author Dilanka Dharmasena
 */
public class PasswordValidation {
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,256})")
    private final String password;

    public PasswordValidation(String password) { this.password = password; }
}
