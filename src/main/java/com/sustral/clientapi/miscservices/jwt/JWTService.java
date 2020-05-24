package com.sustral.clientapi.miscservices.jwt;

import java.util.List;
import java.util.Map;

/**
 * This interface defines methods that enable a caller to issue and validate JSON Web Tokens.
 *
 * @author Dilanka Dharmasena
 */
public interface JWTService {

    /**
     * Creates a JWT with a set of standard claims in addition to the given custom claims.
     *
     * The objects in customClaims will be serialized.
     *
     * @param customClaims  a Map customClaims that contains caller defined claims
     * @return              a String containing the JWT, null if error
     */
    String issueToken(Map<String, Object> customClaims);

    /**
     * Validates a given JWT against a set of standard claims in addition to the given custom claims.
     * This function will also return the claims requested in claimsFilter.
     *
     * The caller must know the types of the requested claims,
     * which are generally the same as the ones sent in by the caller when issuing a token.
     *
     * @param token                 a String containing the JWT
     * @param enforcedCustomClaims  a Map containing the claims and expected values
     * @param claimsFilter          a List containing the keys of the requested claims
     * @return                      a Map containing the requested claims, null if error
     */
    Map<String, Object> validateToken(String token, Map<String, Object> enforcedCustomClaims, List<String> claimsFilter);

}
