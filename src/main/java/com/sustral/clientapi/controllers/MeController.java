package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.helpers.UserEmailHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.me.MeEditRequest;
import com.sustral.clientapi.controllers.types.me.MeResponse;
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
 * This controller handles the /me endpoints.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/me")
public class MeController {

    private final UserService userService;
    private final UserEmailHelper userEmailHelper;

    @Autowired
    public MeController(UserService userService, UserEmailHelper userEmailHelper) {
        this.userService = userService;
        this.userEmailHelper = userEmailHelper;
    }

    @PostMapping(path = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<MeResponse> meEndpoint(HttpServletRequest request, HttpServletResponse response) {
        String userId = new ClaimsRetrievalHelper(request).getUserId();
        UserEntity user = userService.getOneById(userId);
        if (user == null) {
            // Extremely unlikely
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The user no longer exists.", null);
        }

        MeResponse mRes = new MeResponse(user.getId(), user.getEmail(), user.getName(), user.getEmailVerified());

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, mRes);
    }

    @PostMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<MeResponse> meEditEndpoint(@Valid @RequestBody MeEditRequest requestBody,
                                                       HttpServletRequest request, HttpServletResponse response) {
        String userId = new ClaimsRetrievalHelper(request).getUserId();
        UserEntity user = userService.getOneById(userId);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The user no longer exists.", null);
        }

        if (requestBody.getName() != null && !requestBody.getName().isBlank()) {
            UserEntity updatedUser = userService.setName(user, requestBody.getName());
            if (updatedUser == null) {
                response.setStatus(207);
                return new StandardResponse<>("Failed to change name.", null);
            }
            user = updatedUser;
        }

        if (requestBody.getEmail() != null && !requestBody.getEmail().isBlank()) {
            UserEntity updatedUser = userService.setEmail(user, requestBody.getEmail());
            if (updatedUser == null) {
                response.setStatus(207);
                return new StandardResponse<>("Failed to change email.", null);
            }
            user = updatedUser;
            userEmailHelper.beginVerificationProcess(user); // Don't care if it works, the user can just request another
        }

        if (requestBody.getPassword() != null && !requestBody.getPassword().isBlank()) {
            UserEntity updatedUser = userService.setPassword(user, requestBody.getPassword());
            if (updatedUser == null) {
                response.setStatus(207);
                return new StandardResponse<>("Failed to change password.", null);
            }
            user = updatedUser;
        }

        MeResponse mRes = new MeResponse(user.getId(), user.getEmail(), user.getName(), user.getEmailVerified());
        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, mRes);
    }

}
