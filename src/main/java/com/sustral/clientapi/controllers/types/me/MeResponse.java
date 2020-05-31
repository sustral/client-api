package com.sustral.clientapi.controllers.types.me;

/**
 * This class outlines the object returned from the /me endpoint.
 *
 * @author Dilanka Dharmasena
 */
public class MeResponse {

    private String id;
    private String email;
    private String name;
    private boolean emailVerified;

    public MeResponse(String id, String email, String name, boolean emailVerified) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.emailVerified = emailVerified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

}
