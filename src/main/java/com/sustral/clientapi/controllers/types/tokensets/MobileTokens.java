package com.sustral.clientapi.controllers.types.tokensets;

/**
 * Storage type for the tokens required specifically for mobile clients.
 *
 * @author Dilanka Dharmasena
 */
public class MobileTokens extends BaseTokens {

    // Mobile tokens are currently identical to the base token set

    public MobileTokens(String jwt, String session) {
        super(jwt, session);
    }

}
