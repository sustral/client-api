package com.sustral.clientapi.controllers.types.signup;

import javax.validation.constraints.NotNull;

/**
 * This class outlines the expected request body of the /sign_up endpoint.
 *
 * @author Dilanka Dharmasena
 */
public class SignUpRequest {

    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;

    public SignUpRequest(@NotNull String name, @NotNull String email, @NotNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
