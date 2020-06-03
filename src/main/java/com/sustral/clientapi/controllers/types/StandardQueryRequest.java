package com.sustral.clientapi.controllers.types;

import javax.validation.constraints.NotNull;

/**
 * This class outlines the standard JSON request used by most query endpoints.
 *
 * @author Dilanka Dharmasena
 */
public class StandardQueryRequest {

    @NotNull
    private String[] ids;

    public StandardQueryRequest() {
        // For Jackson
    }

    public StandardQueryRequest(@NotNull String[] ids) {
        this.ids = ids;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

}
