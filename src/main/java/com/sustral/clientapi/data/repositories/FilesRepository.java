package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.FilesEntity;
import com.sustral.clientapi.data.models.FilesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<FilesEntity, FilesEntityPK> {
}
