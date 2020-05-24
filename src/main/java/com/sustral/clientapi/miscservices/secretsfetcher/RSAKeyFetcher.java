package com.sustral.clientapi.miscservices.secretsfetcher;

import com.sustral.clientapi.miscservices.secretsfetcher.types.RSAKeyFetcherReturn;

/**
 * This interface defines the methods that can be used to retrieve RSA keys.
 *
 * Implementations are responsible for automatically updating and caching keys.
 *
 * @author Dilanka Dharmasena
 */
public interface RSAKeyFetcher {

    /**
     * Fetches the PrivateKey with the given id.
     *
     * The keyId "current" is reserved for getting the most recent or default key.
     *
     * @param keyId a string keyId specifying the keypair
     * @return      a RSAKeyFetcherReturn
     */
    RSAKeyFetcherReturn fetchPrivateKey(String keyId);

    /**
     * Fetches the PublicKey with the given id.
     *
     * The keyId "current" is reserved for getting the most recent or default key.
     *
     * @param keyId a string keyId specifying the keypair
     * @return      a RSAKeyFetcherReturn
     */
    RSAKeyFetcherReturn fetchPublicKey(String keyId);

}
