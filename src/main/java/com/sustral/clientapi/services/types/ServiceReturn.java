package com.sustral.clientapi.services.types;

/**
 * A generic return type used to interface between controllers and services.
 *
 * @author Dilanka Dharmasena
 * @param <T> the type of the result
 */
public class ServiceReturn<T> {
    private String error; // An error code that indicates an error occurred anytime it is not null
    private T result;

    public ServiceReturn(String error, T result) {
        this.error = error;
        this.result = result;
    }

    public boolean isError() {
        return error.isBlank();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
