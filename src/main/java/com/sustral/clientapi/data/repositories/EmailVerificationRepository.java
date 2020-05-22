package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.EmailVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dilanka Dharmasena
 */
public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, String> {
}
