package com.sustral.clientapi.data.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * This class provides a method to generate UUIDs that conform to company standards.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class CustomUUIDGenerator {

    /**
     * Generates a UUID v4 in String form that does not contain any dashes.
     *
     * @return  a string
     */
    public String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
