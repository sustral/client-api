package com.sustral.clientapi.controllers.helpers;

import com.sustral.clientapi.controllers.types.tokensets.MobileTokens;
import com.sustral.clientapi.controllers.types.tokensets.WebTokens;
import com.sustral.clientapi.utils.ConfigurationParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * This helper class streamlines the use of cookies and headers.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class CookieAndHeaderHelper {

    private final int jwtExpiryDuration;
    private final int sessionExpiryDuration;
    private final boolean productionMode;

    @Autowired
    public CookieAndHeaderHelper(@Value("${deployment.productionMode}") boolean productionMode,
                                 @Value("${sustral.security.jwtExpiration}") String jwtExpiryConfig,
                                 @Value("${sustral.security.sessionExpiration}") String sessionExpiryConfig) {
        this.jwtExpiryDuration = (int) (ConfigurationParser.parseTime(jwtExpiryConfig) / 1000); // ms to s
        this.sessionExpiryDuration = (int) (ConfigurationParser.parseTime(sessionExpiryConfig) / 1000);
        this.productionMode = productionMode;
    }

    /**
     * Sets the auth headers sent to mobile clients.
     */
    public void setMobileAuthHeaders(HttpServletResponse response, MobileTokens tokens) {
        response.setHeader("Access-Control-Expose-Headers", "Authorization, Authorization-Session");
        response.setHeader("Authorization", tokens.getJwt());
        response.setHeader("Authorization-Session", tokens.getSession());

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Sets the auth cookies and headers sent to web clients.
     */
    public void setWebAuthCookiesAndHeaders(HttpServletResponse response, WebTokens tokens) {
        response.setHeader("Access-Control-Expose-Headers", "Sustral-CSRF");
        response.setHeader("Sustral-CSRF", tokens.getCsrf());

        // Change the cookie domains & paths when we split off the auth server
        // so that the session cookie isn't sent everywhere
        Cookie jwtCookie = new Cookie("sustral_accesstoken", tokens.getJwt());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(productionMode); // CHANGE TO TRUE FOR PRODUCTION
        jwtCookie.setMaxAge(jwtExpiryDuration);
        response.addCookie(jwtCookie);

        Cookie sessionCookie = new Cookie("sustral_sessiontoken", tokens.getSession());
        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(productionMode); // CHANGE TO TRUE FOR PRODUCTION
        sessionCookie.setMaxAge(sessionExpiryDuration);
        response.addCookie(sessionCookie);

        response.setStatus(HttpServletResponse.SC_OK);
    }

}
