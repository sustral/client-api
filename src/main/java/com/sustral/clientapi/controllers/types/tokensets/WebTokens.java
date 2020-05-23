package com.sustral.clientapi.controllers.types.tokensets;

/**
 * Storage type for the tokens required specifically for web clients.
 *
 * @author Dilanka Dharmasena
 */
public class WebTokens extends BaseTokens {

    String csrf;

    public WebTokens(String jwt, String session, String csrf) {
        super(jwt, session);
        this.csrf = csrf;
    }

    public String getCsrf() {
        return csrf;
    }

}
