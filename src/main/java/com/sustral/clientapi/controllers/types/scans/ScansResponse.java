package com.sustral.clientapi.controllers.types.scans;

/**
 * This class outlines the response sent from a scan query.
 *
 * @author Dilanka Dharmasena
 */
public class ScansResponse {

    private String id;
    private long updated;
    private String status;
    private String coordinates;

    public ScansResponse(String id, long updated, String status, String coordinates) {
        this.id = id;
        this.updated = updated;
        this.status = status;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

}
