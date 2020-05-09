package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipsEntity;
import com.sustral.clientapi.data.models.UserOrganizationRelationshipsEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrganizationRelationshipsRepository extends JpaRepository<UserOrganizationRelationshipsEntity, UserOrganizationRelationshipsEntityPK> {
}
