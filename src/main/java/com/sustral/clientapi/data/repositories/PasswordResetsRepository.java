package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.PasswordResetsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetsRepository extends JpaRepository<PasswordResetsEntity, String> {
}
