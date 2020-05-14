package com.sustral.clientapi.services;

import com.sustral.clientapi.data.models.ScanEntity;
import com.sustral.clientapi.services.types.ServiceReturn;
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
     * @return          a ServiceReturn containing the ScanEntity
     */
    ServiceReturn<ScanEntity> getOneById(String fieldId, String scanId);

    /**
     * Returns all scans that share a parent field.
     *
     * @param fieldId   a string fieldId of the parent field
     * @return          a ServiceReturn containing a list of ScanEntities
     */
    ServiceReturn<List<ScanEntity>> getManyByFieldId(String fieldId);

    // Mutation Methods

    /**
     * Creates a new scan.
     *
     * @param fieldId       a string fieldId of the parent field
     * @param coordinates   a JTS Polygon coordinates of the parent field at the current time
     * @return              a ServiceReturn containing the new ScanEntity
     */
    ServiceReturn<ScanEntity> create(String fieldId, Polygon coordinates);

    // Utilities

}
