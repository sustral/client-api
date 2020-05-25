package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntityPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Dilanka Dharmasena
 */
public interface UserOrganizationRelationshipRepository extends JpaRepository<UserOrganizationRelationshipEntity, UserOrganizationRelationshipEntityPK> {
    /**
     * Returns all UORs with user_id equal to the given userId.
     *
     * @param userId    a string userId
     * @return          a List of UORs, will not be null
     */
    List<UserOrganizationRelationshipEntity> findAllByUserId(String userId);

    /**
     * @param pageable  a pageable
     * @see #findAllByUserId(String)
     */
    List<UserOrganizationRelationshipEntity> findAllByUserId(String userId, Pageable pageable);

    /**
     * Returns all UORs with organization_id equal to the given organizationId.
     *
     * @param organizationId    a string organizationId
     * @return                  a List of UORs, will not be null
     */
    List<UserOrganizationRelationshipEntity> findAllByOrganizationId(String organizationId);

    /**
     * @param pageable  a pageable
     * @see #findAllByOrganizationId(String)
     */
    List<UserOrganizationRelationshipEntity> findAllByOrganizationId(String organizationId, Pageable pageable);
}
