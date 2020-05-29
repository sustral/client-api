package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.helpers.UserEmailHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.emailverification.FinalizeEmailVerificationRequest;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * This controller handles the /verify_email endpoint.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/verify_email")
public class VerifyEmailController {

    private final UserEmailHelper userEmailHelper;
    private final UserService userService;

    @Autowired
    public VerifyEmailController(UserEmailHelper userEmailHelper, UserService userService) {
        this.userEmailHelper = userEmailHelper;
        this.userService = userService;
    }

    /**
     * Sets email verified to true.
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> verifyEmailEndpoint(@Valid @RequestBody FinalizeEmailVerificationRequest requestBody,
                                                      HttpServletResponse response) {

        int verifySuccess = userEmailHelper.finalizeVerification(requestBody.getToken());
        if (verifySuccess < 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The token is either expired or has been invalidated by another email change.", null);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, null);
    }

    /**
     * Sends an email verification link to a user. This is typically used when a previous link expired or failed to deliver.
     */
    @PostMapping(path = "/request", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> verifyEmailRequestEndpoint(HttpServletRequest request, HttpServletResponse response) {

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        UserEntity user = userService.getOneById(userId);
        if (user == null) {
            // Extremely unlikely
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The user no longer exists.", null);
        }

        int verifySuccess = userEmailHelper.beginVerificationProcess(user);

        if (verifySuccess == -2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The email is already verified.", null);
        } else if (verifySuccess < 0) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new StandardResponse<>(null, null);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, null);
    }

}
