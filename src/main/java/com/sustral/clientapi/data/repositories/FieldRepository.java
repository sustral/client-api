package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<FieldEntity, String> {
}
