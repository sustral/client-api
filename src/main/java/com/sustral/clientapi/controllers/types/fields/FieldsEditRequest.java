package com.sustral.clientapi.controllers.types.fields;

/**
 * This class outlines the user editable fields of a field entity.
 *
 * @author Dilanka Dharmasena
 */
public class FieldsEditRequest {

    private String name;
    private String coordinates;

    public FieldsEditRequest() {
        // For Jackson
    }

    public FieldsEditRequest(String name, String coordinates) {
        this.name = name;
        this.coordinates = coordinates;
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
