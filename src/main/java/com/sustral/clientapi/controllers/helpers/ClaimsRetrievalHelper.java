package com.sustral.clientapi.controllers.helpers;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Provides methods that retrieve claims stored in the JWT.
 *
 * @author Dilanka Dharmasena
 */
public class ClaimsRetrievalHelper {

    private final Map<String, Object> claimsMap;

    public ClaimsRetrievalHelper(HttpServletRequest req) {
        // Guaranteed to exist
        this.claimsMap = (Map<String, Object>) req.getAttribute("claims");
    }

    public String getUserId() {
        // Guaranteed to exist
        return (String) claimsMap.get("user");
    }

    public Object getClaim(String key) {
        return claimsMap.get(key);
    }

}
