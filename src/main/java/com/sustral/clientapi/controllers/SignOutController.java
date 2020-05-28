package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.CookieAndHeaderHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.tokensets.WebTokens;
import com.sustral.clientapi.dataservices.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * This controller handles the /sign_out route.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/sign_out")
public class SignOutController {

    private final CookieAndHeaderHelper cookieAndHeaderHelper;
    private final SessionService sessionService;

    @Autowired
    public SignOutController(CookieAndHeaderHelper cookieAndHeaderHelper, SessionService sessionService) {
        this.cookieAndHeaderHelper = cookieAndHeaderHelper;
        this.sessionService = sessionService;
    }

    /**
     * Signs a user out.
     *
     * Deletes the passed in session token and clears the cookies on web clients.
     */
    @PostMapping(path = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> signOutEndpoint(@RequestHeader("Sustral-Client-Type") String clientType,
                                                  HttpServletRequest request, HttpServletResponse response) {

        String sessionToken = (String) request.getAttribute("sessionToken");
        sessionToken = new String(Base64.getDecoder().decode(sessionToken));
        sessionService.findOneAndDeleteByToken(sessionToken); // Delete the sessionToken

        if (clientType.equals("web")) {
            cookieAndHeaderHelper.setWebAuthCookiesAndHeaders(response, new WebTokens(null, null, null)); // null set tokens
            response.addHeader("Clear-Site-Data", "\"cache\", \"cookies\", \"storage\", \"executionContexts\"");
        }

        response.setStatus(HttpServletResponse.SC_OK);

        return new StandardResponse<>(null, null);
    }

}
