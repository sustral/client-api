package com.sustral.clientapi.controllers.types;

/**
 * This class outlines the standard JSON response used by most endpoints.
 *
 * @author Dilanka Dharmasena
 * @param <T>   The Java/JSON object with the return data
 */
public class StandardResponse<T> {

    private String error;
    private T data;

    public StandardResponse(String error, T data) {
        this.error = error;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
