package com.sustral.clientapi.services;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;

import java.util.List;

/**
 * Defines UOR CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface UserOrganizationRelationshipService {
    // Query Methods

    /**
     * Gets a UOR given the ids of the user and organization.
     *
     * @param userId    a string userId
     * @param orgId     a string orgId
     * @return          a UOR, null if not found or error
     */
    UserOrganizationRelationshipEntity getOneById(String userId, String orgId);

    /**
     * Gets the UORs of the given user.
     *
     * @param userId    a string userId
     * @return          a UORs
     */
    List<UserOrganizationRelationshipEntity> getManyByUserId(String userId);

    /**
     * Gets the UORs of the given organization.
     *
     * @param orgId a string orgId
     * @return      a UORs
     */
    List<UserOrganizationRelationshipEntity> getManyByOrganizationId(String orgId);

    // Mutation Methods

    /**
     * Creates a UOR.
     *
     * @param userId    a string userId
     * @param orgId     a string orgId
     * @return          a UOR
     */
    UserOrganizationRelationshipEntity create(String userId, String orgId);

    // Utilities

}
