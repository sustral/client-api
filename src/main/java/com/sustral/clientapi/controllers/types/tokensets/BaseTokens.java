package com.sustral.clientapi.controllers.types.tokensets;

/**
 * Storage type for the tokens required for all clients.
 *
 * @author Dilanka Dharmasena
 */
public class BaseTokens {

    String jwt;
    String session;

    public BaseTokens(String jwt, String session) {
        this.jwt = jwt;
        this.session = session;
    }

    public String getJwt() {
        return jwt;
    }

    public String getSession() {
        return session;
    }

}
