package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.ScanEntity;
import com.sustral.clientapi.data.models.ScanEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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
}
