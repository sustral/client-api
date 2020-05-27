package com.sustral.clientapi.controllers.types.signin;

import javax.validation.constraints.NotNull;

/**
 * Defines the expected request body for the /sign_in endpoint.
 *
 * @author Dilanka Dharmasena
 */
public class SignInRequest {

    @NotNull
    private String email; // email is validated at the data service layer
    @NotNull
    private String password; // password is validated at the data service layer

    public SignInRequest(@NotNull String email, @NotNull String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
