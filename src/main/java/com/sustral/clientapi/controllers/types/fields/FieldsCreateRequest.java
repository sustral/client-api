package com.sustral.clientapi.controllers.types.fields;

import javax.validation.constraints.NotNull;

/**
 * This class outlines the information needed to create a field entity.
 *
 * @author Dilanka Dharmasena
 */
public class FieldsCreateRequest {

    @NotNull
    private String organization;
    @NotNull
    private String name;
    @NotNull
    private String coordinates;

    public FieldsCreateRequest() {
        // For Jackson
    }

    public FieldsCreateRequest(@NotNull String organization, @NotNull String name, @NotNull String coordinates) {
        this.organization = organization;
        this.name = name;
        this.coordinates = coordinates;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

}
