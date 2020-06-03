package com.sustral.clientapi.controllers.types;

import javax.validation.constraints.NotNull;

/**
 * This class outlines the standard JSON request used by most query endpoints that return a single entity.
 *
 * @author Dilanka Dharmasena
 */
public class StandardSingleQueryRequest {

    @NotNull
    private String id;

    public StandardSingleQueryRequest() {
        // for Jackson
    }

    public StandardSingleQueryRequest(@NotNull String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
