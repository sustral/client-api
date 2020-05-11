package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dilanka Dharmasena
 */
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, String> {
}
