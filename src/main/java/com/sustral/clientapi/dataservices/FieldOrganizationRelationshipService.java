package com.sustral.clientapi.dataservices;

import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;

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
     * @return          an FOR; null if not found or error
     */
    FieldOrganizationRelationshipEntity getOneById(String fieldId, String orgId);

    /**
     * Gets the FORs of the given field.
     *
     * @param fieldId   a string fieldId
     * @param offset    an int offset; a 0 based index from which to begin retrieval
     * @param limit     an int limit; max number of objects to get
     * @return          a list of FORs
     */
    List<FieldOrganizationRelationshipEntity> getManyByFieldId(String fieldId, int offset, int limit);

    List<FieldOrganizationRelationshipEntity> getManyByFieldId(String fieldId);

    /**
     * Gets the FORs of the given organization.
     *
     * @param orgId     a string orgId
     * @param offset    an int offset; a 0 based index from which to begin retrieval
     * @param limit     an int limit; max number of objects to get
     * @return          a list of FORs
     */
    List<FieldOrganizationRelationshipEntity> getManyByOrganizationId(String orgId, int offset, int limit);

    List<FieldOrganizationRelationshipEntity> getManyByOrganizationId(String orgId);

    // Mutation Methods

    /**
     * Creates an FOR.
     *
     * @param fieldId   a string fieldId
     * @param orgId     a string orgId
     * @return          the new FOR
     */
    FieldOrganizationRelationshipEntity create(String fieldId, String orgId);

    // Utilities

}
