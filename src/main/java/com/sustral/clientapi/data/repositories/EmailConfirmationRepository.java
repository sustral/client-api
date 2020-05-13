package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.EmailConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dilanka Dharmasena
 */
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmationEntity, String> {
}
