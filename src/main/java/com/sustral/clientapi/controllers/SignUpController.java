package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.CookieAndHeaderHelper;
import com.sustral.clientapi.controllers.helpers.TokenIssuingHelper;
import com.sustral.clientapi.controllers.helpers.UserEmailHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.signup.SignUpRequest;
import com.sustral.clientapi.controllers.types.tokensets.MobileTokens;
import com.sustral.clientapi.controllers.types.tokensets.WebTokens;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.UserService;
import com.sustral.clientapi.miscservices.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * This controller handles the /sign_up route.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/sign_up")
public class SignUpController {

    private final UserService userService;
    private final TokenIssuingHelper tokenIssuingHelper;
    private final CookieAndHeaderHelper cookieAndHeaderHelper;
    private final UserEmailHelper userEmailHelper;
    private final EmailService emailService;

    @Autowired
    public SignUpController(UserService userService, TokenIssuingHelper tokenIssuingHelper,
                            CookieAndHeaderHelper cookieAndHeaderHelper,
                            UserEmailHelper userEmailHelper, EmailService emailService) {
        this.userService = userService;
        this.tokenIssuingHelper = tokenIssuingHelper;
        this.cookieAndHeaderHelper = cookieAndHeaderHelper;
        this.userEmailHelper = userEmailHelper;
        this.emailService = emailService;
    }

    /**
     * Creates a new user account with the user submitted information.
     *
     * If the user is created, then we will send the onboarding emails and issue tokens.
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> signUpEndpoint(@RequestHeader("Sustral-Client-Type") String clientType,
                                                 @Valid @RequestBody SignUpRequest signUpRequest,
                                                 HttpServletResponse response) {

        if (!clientType.equals("mobile") && !clientType.equals("web")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>(null, null);
        }

        UserEntity user = userService.create(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("A user with that email already exists or the passed in parameters don't meet requirements.",null);
        }

        // A new user has been created, time to send onboarding emails

        userEmailHelper.beginVerificationProcess(user); // Don't care if it works, the user can just request another
        emailService.sendWelcomeEmail(user.getEmail(), user.getName()); // Don't care if it works, the user will be contacted

        // Issue the tokens

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
