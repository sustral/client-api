package com.sustral.clientapi.data.models;

import com.sustral.clientapi.data.types.FileType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "files")
@IdClass(FilesEntityPK.class)
public class FilesEntity {
    private String id;
    private String scanId;
    private Timestamp created;
    private Timestamp updated;
    private FileType fileType;
    private String ffieldId;

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
    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Id
    @Column(name = "ffield_id", nullable = false, length = 32)
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

        FilesEntity that = (FilesEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (scanId != null ? !scanId.equals(that.scanId) : that.scanId != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (fileType != null ? !fileType.equals(that.fileType) : that.fileType != null) return false;
        if (ffieldId != null ? !ffieldId.equals(that.ffieldId) : that.ffieldId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scanId != null ? scanId.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (fileType != null ? fileType.hashCode() : 0);
        result = 31 * result + (ffieldId != null ? ffieldId.hashCode() : 0);
        return result;
    }
}
