package com.sustral.clientapi.controllers.types.resetpassword;

import javax.validation.constraints.NotNull;

/**
 * This class outlines the expected request body when finalizing a password reset (setting the new password).
 *
 * @author Dilanka Dharmasena
 */
public class FinalizePasswordResetRequest {

    @NotNull
    private String token;
    @NotNull
    private String password; // Validated in data layer

    public FinalizePasswordResetRequest() {
        // Required by Jackson
    }

    public FinalizePasswordResetRequest(@NotNull String token, @NotNull String password) {
        this.token = token;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
