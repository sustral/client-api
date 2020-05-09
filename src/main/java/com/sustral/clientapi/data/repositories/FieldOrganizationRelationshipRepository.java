package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldOrganizationRelationshipRepository extends JpaRepository<FieldOrganizationRelationshipEntity, FieldOrganizationRelationshipEntityPK> {
}
