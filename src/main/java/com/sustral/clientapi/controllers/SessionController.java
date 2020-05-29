package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.CookieAndHeaderHelper;
import com.sustral.clientapi.controllers.helpers.TokenIssuingHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.tokensets.MobileTokens;
import com.sustral.clientapi.controllers.types.tokensets.WebTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This controller handles the /session route.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    private final TokenIssuingHelper tokenIssuingHelper;
    private final CookieAndHeaderHelper cookieAndHeaderHelper;

    @Autowired
    public SessionController(TokenIssuingHelper tokenIssuingHelper, CookieAndHeaderHelper cookieAndHeaderHelper) {
        this.tokenIssuingHelper = tokenIssuingHelper;
        this.cookieAndHeaderHelper = cookieAndHeaderHelper;
    }

    @PostMapping(path = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> sessionEndpoint(@RequestHeader("Sustral-Client-Type") String clientType,
                                                  HttpServletRequest request, HttpServletResponse response) {

        String sessionToken = (String) request.getAttribute("sessionToken");

        if (clientType.equals("mobile")) {
            MobileTokens tokens = tokenIssuingHelper.refreshMobileTokens(sessionToken);
            if (tokens == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return new StandardResponse<>(null, null);
            }
            cookieAndHeaderHelper.setMobileAuthHeaders(response, tokens);
        } else if (clientType.equals("web")) {
            WebTokens tokens = tokenIssuingHelper.refreshWebTokens(sessionToken);
            if (tokens == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return new StandardResponse<>(null, null);
            }
            cookieAndHeaderHelper.setWebAuthCookiesAndHeaders(response, tokens);
        }

        return new StandardResponse<>(null, null);
    }
}
