package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.TokenIssuingHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.signin.SignInRequest;
import com.sustral.clientapi.controllers.types.tokensets.MobileTokens;
import com.sustral.clientapi.controllers.types.tokensets.WebTokens;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.UserService;
import com.sustral.clientapi.utils.ConfigurationParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
    private final int jwtExpiryDuration;
    private final int sessionExpiryDuration;
    private final boolean productionMode;

    @Autowired
    public SignInController(UserService userService, TokenIssuingHelper tokenIssuingHelper,
                            @Value("${deployment.productionMode}") boolean productionMode,
                            @Value("${sustral.security.jwtExpiration}") String jwtExpiryConfig,
                            @Value("${sustral.security.sessionExpiration}") String sessionExpiryConfig) {
        this.userService = userService;
        this.tokenIssuingHelper = tokenIssuingHelper;
        this.jwtExpiryDuration = (int) (ConfigurationParser.parseTime(jwtExpiryConfig) / 1000); // ms to s
        this.sessionExpiryDuration = (int) (ConfigurationParser.parseTime(sessionExpiryConfig) / 1000);
        this.productionMode = productionMode;
    }

    /**
     * Authenticates a user based on an email and password.
     *
     *  If the user is authenticated properly, this method will issue them tokens based on the client type.
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> SignInEndpoint(@RequestHeader("Sustral-Client-Test") String clientType,
                                                 @Valid @RequestBody SignInRequest signInRequest,
                                                 HttpServletResponse response) {

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

        // The user is authenticated at this point

        if (clientType.equals("mobile")) {

            MobileTokens tokens = tokenIssuingHelper.issueMobileTokens(user);
            if (tokens == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new StandardResponse<>(null, null);
            }

            response.setHeader("Access-Control-Expose-Headers", "Authorization, Authorization-Session");
            response.setHeader("Authorization", tokens.getJwt());
            response.setHeader("Authorization-Session", tokens.getSession());

            response.setStatus(HttpServletResponse.SC_OK);

        } else if (clientType.equals("web")) {

            WebTokens tokens = tokenIssuingHelper.issueWebTokens(user);
            if (tokens == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new StandardResponse<>(null, null);
            }

            response.setHeader("Access-Control-Expose-Headers", "Sustral-CSRF");
            response.setHeader("Sustral-CSRF", tokens.getCsrf());

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

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return new StandardResponse<>(null, null);
    }

}
