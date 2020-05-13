package com.sustral.clientapi.services;

import com.sustral.clientapi.data.models.OrganizationEntity;
import com.sustral.clientapi.services.types.ServiceReturn;

import java.util.List;

/**
 * Defines Organization CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface OrganizationService {
    // Query Methods

    /**
     * Returns an organization given its id.
     *
     * @param id    a string id
     * @return      a ServiceReturn where the result is of type OrganizationEntity
     */
    ServiceReturn<OrganizationEntity> getOneById(String id);

    /**
     * Returns a list of organizations given a list of ids.
     *
     * @param ids   a list of string ids
     * @return      a ServiceReturn where the result is a list of OrganizationEntities
     */
    ServiceReturn<List<OrganizationEntity>> getManyById(List<String> ids);

    // Mutation Methods

    /**
     * Creates a new organization.
     *
     * Ensures that the given name meets requirements.
     *
     * @param name  a string name
     * @return      a ServiceReturn where the result is of type OrganizationEntity
     */
    ServiceReturn<OrganizationEntity> create(String name);

    /**
     * Updates the name of the given organization.
     *
     * Ensures that the given name meets requirements.
     *
     * @param org   an OrganizationEntity
     * @param name  a string name
     * @return      a ServiceReturn where the result is of type OrganizationEntity
     */
    ServiceReturn<OrganizationEntity> setName(OrganizationEntity org, String name);

    // Utilities

}
