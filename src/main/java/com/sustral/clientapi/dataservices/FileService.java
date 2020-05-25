package com.sustral.clientapi.dataservices;

import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.data.types.FileTypeE;

import java.util.List;

/**
 * Defines File CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface FileService {
    // Query Methods

    /**
     * Returns a single FileEntity with the given id, which is a combination of a fieldId, scanId, and fileId.
     *
     * @param fieldId   a string fieldId of the parent field
     * @param scanId    a string scanId of the parent scan
     * @param fileId    a string fileId
     * @return          a FileEntity; null if not found or error
     */
    FileEntity getOneById(String fieldId, String scanId, String fileId);

    /**
     * Returns a list of FileEntities that share a common parent scan.
     *
     * @param fieldId   a string fieldId of the parent field
     * @param scanId    a string scanId of the parent scan
     * @param offset    an int offset; a 0 based index from which to begin retrieval
     * @param limit     an int limit; max number of objects to get
     * @return          a list of FileEntities
     */
    List<FileEntity> getManyByScanId(String fieldId, String scanId, int offset, int limit);

    // Mutation Methods

    /**
     * Creates a reference to a file; does not store the file itself.
     *
     * @param fieldId   a string fieldId; the id of the parent field
     * @param scanId    a string scanId; the id of the parent scan
     * @param type      a FileTypeE indicating the type of stored file
     * @return          the newly created FileEntity
     */
    FileEntity create(String fieldId, String scanId, FileTypeE type);

    // Utilities

    /**
     * Returns the object store id for the given FileEntity.
     *
     * @param file  a FileEntity that has already been saved
     * @return      the id in string form
     */
    String getObjectId(FileEntity file);

}
