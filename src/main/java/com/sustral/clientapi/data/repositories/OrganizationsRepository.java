package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.OrganizationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationsRepository extends JpaRepository<OrganizationsEntity, String> {
}
