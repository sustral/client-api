package com.sustral.clientapi.controllers.types.files;

/**
 * This class outlines the response to a file query.
 *
 * @author Dilanka Dharmasena
 */
public class FilesResponse {

    private String id;
    private String type;

    public FilesResponse(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
