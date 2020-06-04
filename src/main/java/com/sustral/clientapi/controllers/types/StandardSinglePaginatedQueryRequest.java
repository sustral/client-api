package com.sustral.clientapi.controllers.types;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * This class outlines the standard JSON paginated request used by most endpoints.
 *
 * @author Dilanka Dharmasena
 */
public class StandardSinglePaginatedQueryRequest {

    @NotNull
    private String id;

    @NotNull
    @Min(0)
    private int offset;

    @NotNull
    @Min(1)
    @Max(30)
    private int limit;

    public StandardSinglePaginatedQueryRequest() {
        // For Jackson
    }

    public StandardSinglePaginatedQueryRequest(@NotNull String id, @NotNull @Min(0) int offset, @NotNull @Min(1) @Max(30) int limit) {
        this.id = id;
        this.offset = offset;
        this.limit = limit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
