package com.sustral.clientapi.services;

import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;
import com.sustral.clientapi.services.types.ServiceReturn;

import java.util.List;

/**
 * Defines FOR CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface FieldOrganizationRelationshipService {
    // Query Methods

    /**
     * Gets an FOR given the ids of the field and organization.
     *
     * @param fieldId   a string fieldId
     * @param orgId     a string orgId
     * @return          a ServiceReturn where the result is an FOR
     */
    ServiceReturn<FieldOrganizationRelationshipEntity> getOneById(String fieldId, String orgId);

    /**
     * Gets the FORs of the given field.
     *
     * @param fieldId   a string fieldId
     * @return          a ServiceReturn where the result is a list of FORs
     */
    ServiceReturn<List<FieldOrganizationRelationshipEntity>> getManyByFieldId(String fieldId);

    /**
     * Gets the FORs of the given organization.
     *
     * @param orgId a string orgId
     * @return      a ServiceReturn where the result is a list of FORs
     */
    ServiceReturn<List<FieldOrganizationRelationshipEntity>> getManyByOrganizationId(String orgId);

    // Mutation Methods

    /**
     * Creates an FOR.
     *
     * @param fieldId   a string fieldId
     * @param orgId     a string orgId
     * @return          a ServiceReturn of type FOR
     */
    ServiceReturn<FieldOrganizationRelationshipEntity> create(String fieldId, String orgId);

    // Utilities

}
