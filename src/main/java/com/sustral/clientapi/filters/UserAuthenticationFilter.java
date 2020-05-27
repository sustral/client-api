package com.sustral.clientapi.filters;

import com.sustral.clientapi.miscservices.jwt.JWTService;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * This Filter class is responsible for protecting all the user endpoints that require authentication.
 *
 * This filter must be registered properly.
 *
 * @author Dilanka Dharmasena
 */
public class UserAuthenticationFilter implements Filter {

    private final JWTService jwtService;

    public UserAuthenticationFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Authenticates the requesting user by validating their provided tokens.
     *
     * We discriminate between two client types that have vastly different security features in order
     * to properly secure this API - web-based (cookie-based) clients, and mobile (primarily mobile apps, but
     * essentially anything non-web-based) clients. The company built clients will always set the "Sustral-Client-Type"
     * header.
     *
     * If a client is of the mobile variety, the JSON Web Token is passed in as a Bearer token by way of the "Authorization"
     * header. No other tokens are needed to access these endpoints, but the JWT expires quickly. If the JWT is validated,
     * the request will proceed.
     *
     * If the client is web-based, it is required to pass in a JWT via a secure, httpOnly cookie in addition to a CSRF
     * token in a header name "Sustral-CSRF". This CSRF token is also stored as a claim in the JWT and will be checked
     * during validation.
     *
     * For both types of clients, the filter will place the validated and filtered claims object into the Servlet
     * request for controllers to access further down the chain.
     *
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String clientType = req.getHeader("Sustral-Client-Type");

        if (clientType != null && clientType.equals("mobile")) {

            String authorization = req.getHeader("Authorization");
            if (authorization != null) {
                String jwt = authorization.substring(7); // Remove prepended "Bearer "
                Map<String, Object> filteredClaims = jwtService.validateToken(jwt, null, List.of("user"));
                if (filteredClaims != null) {
                    request.setAttribute("claims", filteredClaims);
                    chain.doFilter(request, response);
                    return;
                }
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        } else if (clientType != null && clientType.equals("web")) {

            Cookie jwtCookie = WebUtils.getCookie(req, "sustral_accesstoken");
            String csrfToken = req.getHeader("Sustral-CSRF");
            if (jwtCookie != null && csrfToken != null) {
                String jwt = jwtCookie.getValue();
                String decodedCsrfToken = new String(Base64.getDecoder().decode(csrfToken));
                Map<String, Object> filteredClaims = jwtService.validateToken(jwt, Map.of("csrf", decodedCsrfToken), List.of("user"));
                if (filteredClaims != null) {
                    request.setAttribute("claims", filteredClaims);
                    chain.doFilter(request, response);
                    return;
                }
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }

        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

}
