package com.sustral.clientapi.data.models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class FileEntityPK implements Serializable {
    private String id;
    private String scanId;
    private String fieldId;

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
    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileEntityPK that = (FileEntityPK) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (scanId != null ? !scanId.equals(that.scanId) : that.scanId != null) return false;
        if (fieldId != null ? !fieldId.equals(that.fieldId) : that.fieldId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scanId != null ? scanId.hashCode() : 0);
        result = 31 * result + (fieldId != null ? fieldId.hashCode() : 0);
        return result;
    }
}
