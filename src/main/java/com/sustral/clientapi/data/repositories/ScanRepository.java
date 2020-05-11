package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.ScanEntity;
import com.sustral.clientapi.data.models.ScanEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dilanka Dharmasena
 */
public interface ScanRepository extends JpaRepository<ScanEntity, ScanEntityPK> {
}
