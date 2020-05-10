package com.sustral.clientapi.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * This class provides a method to generate UUIDs that conform to company standards.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class CustomUUIDGenerator {

    public String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
