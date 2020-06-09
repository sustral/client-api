package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.CookieAndHeaderHelper;
import com.sustral.clientapi.controllers.helpers.TokenIssuingHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.signin.SignInRequest;
import com.sustral.clientapi.controllers.types.tokensets.MobileTokens;
import com.sustral.clientapi.controllers.types.tokensets.WebTokens;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * This controller handles the /sign_in route.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/sign_in")
public class SignInController {

    private final UserService userService;
    private final TokenIssuingHelper tokenIssuingHelper;
    private final CookieAndHeaderHelper cookieAndHeaderHelper;

    @Autowired
    public SignInController(UserService userService, TokenIssuingHelper tokenIssuingHelper,
                            CookieAndHeaderHelper cookieAndHeaderHelper) {
        this.userService = userService;
        this.tokenIssuingHelper = tokenIssuingHelper;
        this.cookieAndHeaderHelper = cookieAndHeaderHelper;
    }

    /**
     * Authenticates a user based on an email and password.
     *
     * If the user is authenticated properly, this method will issue them tokens based on the client type.
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> signInEndpoint(@RequestHeader("Sustral-Client-Type") String clientType,
                                                 @Valid @RequestBody SignInRequest signInRequest,
                                                 HttpServletResponse response) {

        if (!clientType.equals("mobile") && !clientType.equals("web")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>(null, null);
        }

        UserEntity user = userService.getOneByEmail(signInRequest.getEmail());
        if (user == null) { // No user with that email exists
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>(null, null);
        }

        boolean validAuth = userService.validateAuth(user, signInRequest.getPassword());
        if (!validAuth) { // The password is incorrect
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>(null, null);
        }

        // The user is authenticated at this point; issue tokens

        if (clientType.equals("mobile")) {
            MobileTokens tokens = tokenIssuingHelper.issueMobileTokens(user);
            if (tokens == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } else {
                cookieAndHeaderHelper.setMobileAuthHeaders(response, tokens);
            }
        } else if (clientType.equals("web")) {
            WebTokens tokens = tokenIssuingHelper.issueWebTokens(user);
            if (tokens == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } else {
                cookieAndHeaderHelper.setWebAuthCookiesAndHeaders(response, tokens);
            }
        }

        return new StandardResponse<>(null, null);
    }

}
