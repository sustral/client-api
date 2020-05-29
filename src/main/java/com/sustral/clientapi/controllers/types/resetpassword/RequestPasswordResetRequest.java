package com.sustral.clientapi.controllers.types.resetpassword;

import javax.validation.constraints.NotNull;

/**
 * This class outlines the expected request body when requesting a password reset.
 *
 * @author Dilanka Dharmasena
 */
public class RequestPasswordResetRequest {

    @NotNull
    private String email;

    public RequestPasswordResetRequest() {
        // Required by Jackson
    }

    public RequestPasswordResetRequest(@NotNull String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
