package com.sustral.clientapi.data.models;

import com.sustral.clientapi.data.types.ScanStatus;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "scans")
@IdClass(ScansEntityPK.class)
public class ScansEntity {
    private String id;
    private String ffieldId;
    private Timestamp created;
    private Timestamp updated;
    private ScanStatus scanStatus;

    @Id
    @Column(name = "id", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "ffield_id", nullable = false, length = 32)
    public String getFfieldId() {
        return ffieldId;
    }

    public void setFfieldId(String ffieldId) {
        this.ffieldId = ffieldId;
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

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "scan_status", nullable = false)
    public ScanStatus getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(ScanStatus scanStatus) {
        this.scanStatus = scanStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScansEntity that = (ScansEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (ffieldId != null ? !ffieldId.equals(that.ffieldId) : that.ffieldId != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (scanStatus != null ? !scanStatus.equals(that.scanStatus) : that.scanStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ffieldId != null ? ffieldId.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (scanStatus != null ? scanStatus.hashCode() : 0);
        return result;
    }
}
