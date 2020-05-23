package com.sustral.clientapi.services;

import java.io.InputStream;

/**
 * Defines Object CRUD services and utilities.
 *
 * @author Dilanka Dharmasena
 */
public interface ObjectService {
    // Query Methods

    /**
     * Gets the object with the given id.
     *
     * The returned InputStream must be closed as soon as possible.
     *
     * @param id    a string id
     * @return      an InputStream containing the contents of the object; null if not found or error
     */
    InputStream getOneById(String id);

    // Mutation Methods

    /**
     * Sets the object with the given id to the given object.
     *
     * @param id    a string id
     * @param obj   an InputStream containing the object
     * @return      an int; 0 if successful, negative otherwise
     */
    int setOneById(String id, InputStream obj);

    // Add overloads with different ways to input objects (eg. file)

    // Utilities

}
