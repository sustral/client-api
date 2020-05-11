package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Dilanka Dharmasena
 */
public interface FieldOrganizationRelationshipRepository extends JpaRepository<FieldOrganizationRelationshipEntity, FieldOrganizationRelationshipEntityPK> {
    /**
     * Returns all FORs where ffield_id equals the given field id.
     *
     * @param fieldId   a string fieldId
     * @return          a List of FORs, will not be null
     */
    List<FieldOrganizationRelationshipEntity> findAllByFieldId(String fieldId);

    /**
     * Returns all FORs where organization_id equals the given organization id.
     *
     * @param organizationId    a string organizationId
     * @return                  a List of FORs, will not be null
     */
    List<FieldOrganizationRelationshipEntity> findAllByOrganizationId(String organizationId);
}
