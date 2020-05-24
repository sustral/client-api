package com.sustral.clientapi.dataservices.types;

/**
 * A return type that holds both a token and the db entity with its hashed counterpart.
 *
 * @author Dilanka Dharmasena
 */
public class TokenWrapper<A, B> {

    private A token;
    private B entity;

    public TokenWrapper(A token, B entity) {
        this.token = token;
        this.entity = entity;
    }

    public A getToken() {
        return token;
    }

    public void setToken(A token) {
        this.token = token;
    }

    public B getEntity() {
        return entity;
    }

    public void setEntity(B entity) {
        this.entity = entity;
    }

}
