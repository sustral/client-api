package com.sustral.clientapi.miscservices.jwt;

import com.sustral.clientapi.miscservices.secretsfetcher.RSAKeyFetcher;
import com.sustral.clientapi.miscservices.secretsfetcher.types.RSAKeyFetcherReturn;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is an implementation of JWTService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class JWTServiceImpl implements JWTService {

    private static final long FIFTEEN_MINUTES = 1000 * 60 * 15; // fifteen minutes in ms

    private final RSAKeyFetcher keyFetcher;

    /**
     * Helps retrieve the proper signing key since the same key is not used for every JWT.
     */
    private class CustomSigningKeyResolver extends SigningKeyResolverAdapter {
        @Override
        public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
            String keyId = (String) jwsHeader.get("kid"); // Guaranteed to be a String in a valid token
            RSAKeyFetcherReturn keyFetcherReturn = keyFetcher.fetchPublicKey(keyId);
            if (!keyFetcherReturn.isPresent()) { return null; } // Will cause an exception to be thrown
            return keyFetcherReturn.getKey();
        }
    }

    @Autowired
    public JWTServiceImpl(RSAKeyFetcher keyFetcher) {
        this.keyFetcher = keyFetcher;
    }

    @Override
    public String issueToken(Map<String, Object> customClaims) {

        // Blank/Null checks removed for performance
        if (customClaims == null) {
            return null;
        }

        // Get a private rsa key and associated keyId
        RSAKeyFetcherReturn keyFetcherReturn = keyFetcher.fetchPrivateKey("current"); // most recent key works
        if (!keyFetcherReturn.isPresent()) { return null; }
        String keyId = keyFetcherReturn.getKeyId();
        Key privateKey = keyFetcherReturn.getKey();

        // Create JWT Headers
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "PS256"); // may get overwritten by library to the same value
        headers.put("kid", keyId);

        // Create JWT Claims
        long currTime = System.currentTimeMillis();
        long expTime = currTime + FIFTEEN_MINUTES;

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "com.sustral");
        claims.put("iat", currTime);
        claims.put("exp", expTime);
        claims.putAll(customClaims); // Copy over custom claims

        // Create and sign JWT
        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .signWith(privateKey, SignatureAlgorithm.PS256)
                .compact();
    }

    @Override
    public Map<String, Object> validateToken(String token, Map<String, Object> enforcedCustomClaims, List<String> claimsFilter) {

        // Blank/Null checks removed for performance

        if (token == null || token.isBlank()) {
            return null;
        }


        if (enforcedCustomClaims == null) {
            return null;
        }

        if (claimsFilter == null) {
            return null;
        }

        // Check JWT signature
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKeyResolver(new CustomSigningKeyResolver())
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            // No further handling needed as this is an expected error
            // This signifies that the JWT is invalid
            return null;
        }

        // At this point, the JWT signature is deemed valid

        JwsHeader headers = jws.getHeader(); // Forced to be raw
        Claims claims = jws.getBody();

        // The below technique is not the one recommended by the JWT library
        // Check headers
        if (!headers.get("alg").equals("PS256")) { return null; }

        // Check claims
        if (!claims.get("iss").equals("com.sustral")) { return null; }
        if ((long) claims.get("exp") < System.currentTimeMillis()) { return null; }

        // Check custom claims
        for (Map.Entry<String, Object> entry: enforcedCustomClaims.entrySet()) {
            if (!claims.get(entry.getKey()).equals(entry.getValue())) { return null; }
        }

        // At this point, the headers and claims are deemed valid

        // Filter the caller's requested claims
        Map<String, Object> returnMap = new HashMap<>();
        for (String key: claimsFilter) {
            returnMap.put(key, claims.get(key));
        }

        return returnMap;

    }

}
