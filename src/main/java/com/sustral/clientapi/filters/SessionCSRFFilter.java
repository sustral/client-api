package com.sustral.clientapi.filters;

import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter is responsible for checking the existence and validity of CRSF tokens for requests sent
 * from web clients to endpoints that require a session token.
 *
 * This filter must be registered.
 *
 * @author Dilanka Dharmasena
 */
public class SessionCSRFFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String clientType = req.getHeader("Sustral-Client-Type");

        if (clientType != null && clientType.equals("mobile")) {
            // No need to check CSRF for mobile clients
            chain.doFilter(request, response);
            return;
        } else if (clientType != null && clientType.equals("web")) {

            Cookie sessionCookie = WebUtils.getCookie(req, "sustral_sessiontoken");
            String csrfToken = req.getHeader("Sustral-CSRF");

            if (sessionCookie != null && csrfToken != null) {
                String sessionToken = sessionCookie.getValue();
                String csrfTokenFromSession = sessionToken.split("[.]", 2)[1];
                // No need to decode Base64
                if (csrfToken.equals(csrfTokenFromSession)) {
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
