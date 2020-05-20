package com.sustral.clientapi.data.utils.idgenerator;

/**
 * This defines a method that generates String IDs that conform to standards.
 *
 * @author Dilanka Dharmasena
 */
public interface IdGenerator {

    /**
     * Generates a correctly formed id in a sufficiently random manner.
     *
     * @return  a String that can be used as an ID in Sustral entities
     */
    String generateId();

}
