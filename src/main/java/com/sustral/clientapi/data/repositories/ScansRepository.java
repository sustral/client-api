package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.ScansEntity;
import com.sustral.clientapi.data.models.ScansEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScansRepository extends JpaRepository<ScansEntity, ScansEntityPK> {
}
