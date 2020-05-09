package com.sustral.clientapi.data.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class FfieldOrganizationRelationshipsEntityPK implements Serializable {
    private String ffieldId;
    private String organizationId;

    @Column(name = "ffield_id", nullable = false, length = 32)
    @Id
    public String getFfieldId() {
        return ffieldId;
    }

    public void setFfieldId(String ffieldId) {
        this.ffieldId = ffieldId;
    }

    @Column(name = "organization_id", nullable = false, length = 32)
    @Id
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FfieldOrganizationRelationshipsEntityPK that = (FfieldOrganizationRelationshipsEntityPK) o;

        if (ffieldId != null ? !ffieldId.equals(that.ffieldId) : that.ffieldId != null) return false;
        if (organizationId != null ? !organizationId.equals(that.organizationId) : that.organizationId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ffieldId != null ? ffieldId.hashCode() : 0;
        result = 31 * result + (organizationId != null ? organizationId.hashCode() : 0);
        return result;
    }
}
