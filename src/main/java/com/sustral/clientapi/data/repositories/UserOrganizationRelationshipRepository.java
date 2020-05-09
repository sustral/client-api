package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrganizationRelationshipRepository extends JpaRepository<UserOrganizationRelationshipEntity, UserOrganizationRelationshipEntityPK> {
}
