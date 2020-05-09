package com.sustral.clientapi.data.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ffield_organization_relationships")
@IdClass(FfieldOrganizationRelationshipsEntityPK.class)
public class FfieldOrganizationRelationshipsEntity {
    private String ffieldId;
    private String organizationId;
    private Timestamp created;
    private Timestamp updated;

    @Id
    @Column(name = "ffield_id", nullable = false, length = 32)
    public String getFfieldId() {
        return ffieldId;
    }

    public void setFfieldId(String ffieldId) {
        this.ffieldId = ffieldId;
    }

    @Id
    @Column(name = "organization_id", nullable = false, length = 32)
    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "updated", nullable = false)
    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FfieldOrganizationRelationshipsEntity that = (FfieldOrganizationRelationshipsEntity) o;

        if (ffieldId != null ? !ffieldId.equals(that.ffieldId) : that.ffieldId != null) return false;
        if (organizationId != null ? !organizationId.equals(that.organizationId) : that.organizationId != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ffieldId != null ? ffieldId.hashCode() : 0;
        result = 31 * result + (organizationId != null ? organizationId.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        return result;
    }
}
