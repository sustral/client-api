package com.sustral.clientapi.data.models;

import com.sustral.clientapi.data.types.FileTypeE;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "files")
@IdClass(FileEntityPK.class)
public class FileEntity {
    private String id;
    private String scanId;
    private Timestamp created;
    private Timestamp updated;
    private FileTypeE fileType;
    private String fieldId;

    @Id
    @Column(name = "id", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "scan_id", nullable = false, length = 32)
    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
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
    @Column(name = "file_type", nullable = false)
    public FileTypeE getFileType() {
        return fileType;
    }

    public void setFileType(FileTypeE fileType) {
        this.fileType = fileType;
    }

    @Id
    @Column(name = "ffield_id", nullable = false, length = 32)
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

        FileEntity that = (FileEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (scanId != null ? !scanId.equals(that.scanId) : that.scanId != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (fileType != null ? !fileType.equals(that.fileType) : that.fileType != null) return false;
        if (fieldId != null ? !fieldId.equals(that.fieldId) : that.fieldId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scanId != null ? scanId.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + (fieldId != null ? fieldId.hashCode() : 0);
        return result;
    }
}
