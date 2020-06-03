package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardQueryRequest;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.users.UsersResponse;
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
import java.util.ArrayList;
import java.util.List;

/**
 * This controller handles the /users routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    private final AuthorizationHelper authorizationHelper;
    private final UserService userService;

    @Autowired
    public UsersController(AuthorizationHelper authorizationHelper, UserService userService) {
        this.authorizationHelper = authorizationHelper;
        this.userService = userService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<UsersResponse>> usersEndpoint(@Valid @RequestBody StandardQueryRequest requestBody,
                                                               HttpServletRequest request, HttpServletResponse response) {

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessUsers(userId, requestBody.getIds());

        List<UsersResponse> responseBody = new ArrayList<>();
        for (int i = 0; i < requestBody.getIds().length; i++) {
            if (authorizedToAccess[i]) {
                UserEntity tempUser = userService.getOneById(requestBody.getIds()[i]);
                if (tempUser != null) {
                    responseBody.add(new UsersResponse(tempUser.getId(), tempUser.getEmail(), tempUser.getName()));
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, responseBody);
    }

}
