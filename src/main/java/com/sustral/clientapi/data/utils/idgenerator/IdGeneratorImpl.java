package com.sustral.clientapi.data.utils.idgenerator;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * This class is an implementation of IdGenerator.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class IdGeneratorImpl implements IdGenerator {

    // Generates a UUID v4 in String form that does not contain any dashes.
    @Override
    public String generateId() {

        return UUID.randomUUID().toString().replace("-", "");

    }

}
