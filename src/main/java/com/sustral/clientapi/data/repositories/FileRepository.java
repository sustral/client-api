package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.data.models.FileEntityPK;
import com.sustral.clientapi.data.types.FileTypeE;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Dilanka Dharmasena
 */
public interface FileRepository extends JpaRepository<FileEntity, FileEntityPK> {
    /**
     *  Returns a list of files that share the same fieldId and scanId.
     *
     * @param fieldId   a string fieldId
     * @param scanId    a string scanId
     * @return          a list of FileEntities
     */
    List<FileEntity> findAllByFieldIdAndScanId(String fieldId, String scanId);

    /**
     * @param pageable  a pageable
     * @see #findAllByFieldIdAndScanId(String, String)
     */
    List<FileEntity> findAllByFieldIdAndScanId(String fieldId, String scanId, Pageable pageable);

    /**
     *  Returns a list of files that share the same fieldId and scanId, and are of a given fileType.
     *
     * @param fieldId   a string fieldId
     * @param scanId    a string scanId
     * @param fileType  a FileTypeE fileType
     * @return          a list of FileEntities
     */
    List<FileEntity> findAllByFieldIdAndScanIdAndFileType(String fieldId, String scanId, FileTypeE fileType);

    /**
     * @param pageable  a pageable
     * @see #findAllByFieldIdAndScanIdAndFileType(String, String, FileTypeE)
     */
    List<FileEntity> findAllByFieldIdAndScanIdAndFileType(String fieldId, String scanId, FileTypeE fileType, Pageable pageable);
}
