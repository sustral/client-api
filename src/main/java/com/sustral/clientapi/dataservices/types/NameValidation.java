package com.sustral.clientapi.dataservices.types;

import javax.validation.constraints.Size;

/**
 * Class used for validating user, organization, and field names.
 *
 * @author Dilanka Dharmasena
 */
public class NameValidation {
    @Size(min = 1, max = 100)
    private final String name;

    public NameValidation(String name) { this.name = name; }
}
