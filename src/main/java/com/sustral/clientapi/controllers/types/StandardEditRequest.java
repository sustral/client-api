package com.sustral.clientapi.controllers.types;

import javax.validation.constraints.NotNull;

/**
 * This class outlines the standard JSON edit request used by most endpoints.
 *
 * @author Dilanka Dharmasena
 * @param <T>   The Java/JSON object with the edit data object
 */
public class StandardEditRequest<T> {

    @NotNull
    private String id;
    @NotNull
    private T data;

    public StandardEditRequest() {
        // Required by Jackson
    }

    public StandardEditRequest(@NotNull String id, @NotNull T data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
