package com.sustral.clientapi.controllers.types.organizations;

/**
 * This class outlines the response of an organization query.
 *
 * @author Dilanka Dharmasena
 */
public class OrganizationsResponse {

    private String id;
    private String name;

    public OrganizationsResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
