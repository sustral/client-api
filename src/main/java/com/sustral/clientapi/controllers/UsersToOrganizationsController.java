package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.StandardSinglePaginatedQueryRequest;
import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import com.sustral.clientapi.dataservices.UserOrganizationRelationshipService;
import org.bouncycastle.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This controller handles the /users_to_organizations routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/users_to_organizations")
public class UsersToOrganizationsController {

    private final AuthorizationHelper authorizationHelper;
    private final UserOrganizationRelationshipService uorService;

    @Autowired
    public UsersToOrganizationsController(AuthorizationHelper authorizationHelper, UserOrganizationRelationshipService uorService) {
        this.authorizationHelper = authorizationHelper;
        this.uorService = uorService;
    }

    @PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<String>> usersToOrganizationsUsersEndpoint(@Valid @RequestBody StandardSinglePaginatedQueryRequest requestBody,
                                                                            HttpServletRequest request, HttpServletResponse response) {
        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessOrganizations(userId, Arrays.append(new String[] {}, requestBody.getId()));
        if (Boolean.FALSE.equals(authorizedToAccess[0])) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>("The user does not have access to this organization.", null);
        }

        List<UserOrganizationRelationshipEntity> uors = uorService.getManyByOrganizationId(requestBody.getId(), requestBody.getOffset(), requestBody.getLimit());

        List<String> userIds = uors.stream().map(UserOrganizationRelationshipEntity::getUserId).collect(Collectors.toList());

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, userIds);
    }

    @PostMapping(path = "/organizations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<String>> usersToOrganizationsOrganizationsEndpoint(@Valid @RequestBody StandardSinglePaginatedQueryRequest requestBody,
                                                                                    HttpServletRequest request, HttpServletResponse response) {
        String userId = new ClaimsRetrievalHelper(request).getUserId();
        if (!userId.equals(requestBody.getId())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>("The user does not have access to this user.", null);
        }

        List<UserOrganizationRelationshipEntity> uors = uorService.getManyByUserId(requestBody.getId(), requestBody.getOffset(), requestBody.getLimit());

        List<String> orgIds = uors.stream().map(UserOrganizationRelationshipEntity::getOrganizationId).collect(Collectors.toList());

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, orgIds);
    }

}
