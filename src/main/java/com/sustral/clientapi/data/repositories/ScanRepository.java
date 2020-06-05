package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.ScanEntity;
import com.sustral.clientapi.data.models.ScanEntityPK;
import com.sustral.clientapi.data.types.ScanStatusE;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Dilanka Dharmasena
 */
public interface ScanRepository extends JpaRepository<ScanEntity, ScanEntityPK> {
    /**
     * Returns all ScanEntities that share a parent field.
     *
     * @param fieldId   a String fieldId
     * @return          a List of ScanEntities
     */
    List<ScanEntity> findAllByFieldId(String fieldId);

    /**
     * @param pageable  a pageable
     * @see #findAllByFieldId(String)
     */
    List<ScanEntity> findAllByFieldId(String fieldId, Pageable pageable);

    /**
     * Returns the oldest scan with a given status.
     *
     * @param scanStatus    a ScanStatusE
     * @return              an Optional containing a ScanEntity
     */
    Optional<ScanEntity> findFirstByScanStatusOrderByCreatedAsc(ScanStatusE scanStatus);
}
