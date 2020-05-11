package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.data.models.FileEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dilanka Dharmasena
 */
public interface FileRepository extends JpaRepository<FileEntity, FileEntityPK> {
}
