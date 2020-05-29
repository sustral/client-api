package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.PasswordResetHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.resetpassword.FinalizePasswordResetRequest;
import com.sustral.clientapi.controllers.types.resetpassword.RequestPasswordResetRequest;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * This controller handles the /reset_password route.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/reset_password")
public class ResetPasswordController {

    private final PasswordResetHelper passwordResetHelper;
    private final UserService userService;

    @Autowired
    public ResetPasswordController(PasswordResetHelper passwordResetHelper, UserService userService) {
        this.passwordResetHelper = passwordResetHelper;
        this.userService = userService;
    }

    /**
     * Sets a new password.
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> resetPasswordEndpoint(@Valid @RequestBody FinalizePasswordResetRequest requestBody,
                                                        HttpServletResponse response) {

        int resetSuccess = passwordResetHelper.finalizePasswordReset(requestBody.getToken(), requestBody.getPassword());

        if (resetSuccess < 0 ) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The token may be expired or the password may not meet requirements.", null);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, null);
    }

    /**
     * Initiates the password reset process for the user with the given email.
     */
    @PostMapping(path = "/request", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> resetPasswordRequestEndpoint(@Valid @RequestBody RequestPasswordResetRequest requestBody,
                                                               HttpServletResponse response) {

        UserEntity user = userService.getOneByEmail(requestBody.getEmail());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("No account with that email exists.", null);
        }

        int resetSuccess = passwordResetHelper.beginPasswordResetProcess(user);
        if (resetSuccess < 0) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new StandardResponse<>(null, null);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, null);
    }

}
