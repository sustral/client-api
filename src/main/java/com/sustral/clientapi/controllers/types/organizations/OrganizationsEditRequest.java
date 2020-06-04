package com.sustral.clientapi.controllers.types.organizations;

/**
 * This class outlines the user editable fields of an organization entity.
 *
 * @author Dilanka Dharmasena
 */
public class OrganizationsEditRequest {

    private String name;

    public OrganizationsEditRequest() {
        // For Jackson
    }

    public OrganizationsEditRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
