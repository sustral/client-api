package com.sustral.clientapi.controllers.types.fields;

/**
 * This class outlines the response to a field query.
 *
 * @author Dilanka Dharmasena
 */
public class FieldsResponse {

    private String id;
    private String name;
    private String coordinates;
    private boolean approved;

    public FieldsResponse(String id, String name, String coordinates, boolean approved) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.approved = approved;
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

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}
