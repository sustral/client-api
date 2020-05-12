package com.sustral.clientapi.services;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import com.sustral.clientapi.services.types.ServiceReturn;

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
     * @return          a ServiceReturn where the result is a UOR
     */
    ServiceReturn<UserOrganizationRelationshipEntity> getOneById(String userId, String orgId);

    /**
     * Gets the UORs of the given user.
     *
     * @param userId    a string userId
     * @return          a ServiceReturn where the result is a list of UORs
     */
    ServiceReturn<List<UserOrganizationRelationshipEntity>> getManyByUserId(String userId);

    /**
     * Gets the UORs of the given organization.
     *
     * @param orgId a string orgId
     * @return      a ServiceReturn where the result is a list of UORs
     */
    ServiceReturn<List<UserOrganizationRelationshipEntity>> getManyByOrganizationId(String orgId);

    // Mutation Methods

    /**
     * Creates a UOR.
     *
     * @param userId    a string userId
     * @param orgId     a string orgId
     * @return          a ServiceReturn of type UOR
     */
    ServiceReturn<UserOrganizationRelationshipEntity> create(String userId, String orgId);

    // Utilities

}
