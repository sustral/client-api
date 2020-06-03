package com.sustral.clientapi.controllers.types.users;

/**
 * This class outlines the fields that will be returned from a user query.
 *
 * @author Dilanka Dharmasena
 */
public class UsersResponse {

    private String id;
    private String email;
    private String name;

    public UsersResponse(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
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

}
