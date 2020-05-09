package com.sustral.clientapi.data.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class FilesEntityPK implements Serializable {
    private String id;
    private String scanId;
    private String ffieldId;

    @Column(name = "id", nullable = false, length = 32)
    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "scan_id", nullable = false, length = 32)
    @Id
    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    @Column(name = "ffield_id", nullable = false, length = 32)
    @Id
    public String getFfieldId() {
        return ffieldId;
    }

    public void setFfieldId(String ffieldId) {
        this.ffieldId = ffieldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilesEntityPK that = (FilesEntityPK) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (scanId != null ? !scanId.equals(that.scanId) : that.scanId != null) return false;
        if (ffieldId != null ? !ffieldId.equals(that.ffieldId) : that.ffieldId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scanId != null ? scanId.hashCode() : 0);
        result = 31 * result + (ffieldId != null ? ffieldId.hashCode() : 0);
        return result;
    }
}
