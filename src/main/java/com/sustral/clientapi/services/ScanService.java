package com.sustral.clientapi.services;

import com.sustral.clientapi.data.models.ScanEntity;
import org.locationtech.jts.geom.Polygon;

import java.util.List;

/**
 * Defines Scan CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface ScanService {
    // Query Methods

    /**
     * Returns the ScanEntity with the given id, which is made up of the fieldId and scanId.
     *
     * @param fieldId   a string fieldId of the parent field
     * @param scanId    a string scanId
     * @return          a ScanEntity; null if not found or error
     */
    ScanEntity getOneById(String fieldId, String scanId);

    /**
     * Returns all scans that share a parent field.
     *
     * @param fieldId   a string fieldId of the parent field
     * @return          a list of ScanEntities
     */
    List<ScanEntity> getManyByFieldId(String fieldId);

    // Mutation Methods

    /**
     * Creates a new scan.
     *
     * @param fieldId       a string fieldId of the parent field
     * @param coordinates   a JTS Polygon coordinates of the parent field at the current time
     * @return              a new ScanEntity
     */
    ScanEntity create(String fieldId, Polygon coordinates);

    // Utilities

}
