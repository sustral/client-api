package com.sustral.clientapi.dataservices;

import com.sustral.clientapi.data.models.FieldEntity;
import org.locationtech.jts.geom.Polygon;

import java.util.List;

/**
 * Defines Field CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface FieldService {
    // Query Methods

    /**
     * Returns the field with the given id.
     *
     * @param id    a string id
     * @return      a FieldEntity; null if not found or error
     */
    FieldEntity getOneById(String id);

    /**
     * Returns the fields with the given ids.
     *
     * @param ids   a list of strings ids
     * @return      a list of FieldEntities
     */
    List<FieldEntity> getManyById(List<String> ids);

    // Mutation Methods

    /**
     * Creates a field.
     *
     * Ensures that the given name meets requirements.
     * Ensures that the given coordinates meet requirements.
     * Sets the approval status to false pending review.
     *
     * @param name          a string name
     * @param coordinates   a JTS Polygon
     * @return              the new FieldEntity
     */
    FieldEntity create(String name, Polygon coordinates);

    /**
     * Changes the name of the given field.
     *
     * Ensures that the given name meets requirements.
     *
     * @param field a FieldEntity field
     * @param name  a string name
     * @return      the updated FieldEntity
     */
    FieldEntity setName(FieldEntity field, String name);

    /**
     * Changes the coordinates of the given field.
     *
     * Ensures that the given coordinates meet requirements.
     * Changes the approval status to false pending review.
     *
     * @param field         a FieldEntity
     * @param coordinates   a JTS Polygon
     * @return              the updated FieldEntity
     */
    FieldEntity setCoordinates(FieldEntity field, Polygon coordinates);

    /**
     * Changes the approval status of the given field.
     *
     * @param field     a FieldEntity
     * @param approved  a boolean
     * @return          the updated FieldEntity
     */
    FieldEntity setApproved(FieldEntity field, boolean approved);

    // Utilities

}
