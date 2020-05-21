package com.sustral.clientapi.utils.secretsfetcher.types;

import java.security.Key;

/**
 * This return type is used with RSAKeyFetcher to return a key along with its id.
 *
 * A null keyId indicates that the key does not exist or that an error has occurred.
 *
 * @author Dilanka Dharmasena
 */
public class RSAKeyFetcherReturn {

    private String keyId;
    private Key key;

    public RSAKeyFetcherReturn(String keyId, Key key) {
        this.keyId = keyId;
        this.key = key;
    }

    public boolean isPresent() {
        return (!keyId.isBlank() && key != null);
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

}
