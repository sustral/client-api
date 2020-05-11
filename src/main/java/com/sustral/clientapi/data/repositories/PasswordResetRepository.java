package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.PasswordResetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dilanka Dharmasena
 */
public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity, String> {
}
