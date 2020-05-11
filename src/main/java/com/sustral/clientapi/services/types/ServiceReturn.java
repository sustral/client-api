package com.sustral.clientapi.services.types;

/**
 * A generic return type used to interface between controllers and services.
 *
 * @author Dilanka Dharmasena
 * @param <T> the type of the result
 */
public class ServiceReturn<T> {
    private boolean error;
    private String errorMessage;
    private String passthroughMessage; // Designed to pass messages through to front end without details of backend specifics
    private T result;

    public ServiceReturn(boolean error, String errorMessage, String passthroughMessage, T result) {
        this.error = error;
        this.errorMessage = errorMessage;
        this.passthroughMessage = passthroughMessage;
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPassthroughMessage() {
        return passthroughMessage;
    }

    public void setPassthroughMessage(String passthroughMessage) {
        this.passthroughMessage = passthroughMessage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
