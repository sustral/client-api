package com.sustral.clientapi.controllers.types.emailverification;

import javax.validation.constraints.NotNull;

/**
 * This class outlines the expected request body when verifying an email address.
 *
 * @author Dilanka Dharmasena
 */
public class FinalizeEmailVerificationRequest {

    @NotNull
    private String token;

    public FinalizeEmailVerificationRequest() {
        // Required by Jackson
    }

    public FinalizeEmailVerificationRequest(@NotNull String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
