package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardEditRequest;
import com.sustral.clientapi.controllers.types.StandardQueryRequest;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.organizations.OrganizationsEditRequest;
import com.sustral.clientapi.controllers.types.organizations.OrganizationsResponse;
import com.sustral.clientapi.data.models.OrganizationEntity;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import com.sustral.clientapi.dataservices.OrganizationService;
import com.sustral.clientapi.dataservices.UserOrganizationRelationshipService;
import com.sustral.clientapi.dataservices.UserService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * This controller handles the /organizations routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/organizations")
public class OrganizationsController {

    private final AuthorizationHelper authorizationHelper;
    private final UserService userService;
    private final OrganizationService organizationService;
    private final UserOrganizationRelationshipService uorService;

    @Autowired
    public OrganizationsController(AuthorizationHelper authorizationHelper, UserService userService,
                                   OrganizationService organizationService, UserOrganizationRelationshipService uorService) {
        this.authorizationHelper = authorizationHelper;
        this.userService = userService;
        this.organizationService = organizationService;
        this.uorService = uorService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<OrganizationsResponse>> organizationsEndpoint(@Valid @RequestBody StandardQueryRequest requestBody,
                                                                               HttpServletRequest request, HttpServletResponse response) {

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessOrganizations(userId, requestBody.getIds());

        List<OrganizationsResponse> responseBody = new ArrayList<>();
        for (int i = 0; i < requestBody.getIds().length; i++) {
            if (authorizedToAccess[i]) {
                OrganizationEntity tempOrg = organizationService.getOneById(requestBody.getIds()[i]);
                if (tempOrg != null) {
                    responseBody.add(new OrganizationsResponse(tempOrg.getId(), tempOrg.getName()));
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, responseBody);
    }

    @PostMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<OrganizationsResponse> organizationsEditEndpoint(@Valid @RequestBody StandardEditRequest<OrganizationsEditRequest> requestBody,
                                                                             HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = "";

        String userId = new ClaimsRetrievalHelper(request).getUserId();

        Boolean[] authorizedToAccess = authorizationHelper.canAccessOrganizations(userId, Arrays.append(new String[] {}, requestBody.getId()));
        if (!authorizedToAccess[0]) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>("The user does not have access to this organization.", null);
        }

        UserEntity user = userService.getOneById(userId);
        if (user == null || !user.getEmailVerified()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The user's email is not verified.", null);
            // The user could also no longer exist but this is extremely unlikely.
        }

        OrganizationEntity org = organizationService.getOneById(requestBody.getId());
        if (org == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The organization does not exist.", null);
        }

        if (requestBody.getData().getName() != null && !requestBody.getData().getName().isBlank()) {
            OrganizationEntity updatedOrg = organizationService.setName(org, requestBody.getData().getName());
            if (updatedOrg == null) {
                response.setStatus(207);
                errorMessage += "Failed to change name. ";
            } else {
                org = updatedOrg;
            }
        }

        OrganizationsResponse mRes = new OrganizationsResponse(org.getId(), org.getName());
        if (response.getStatus() != 207) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return new StandardResponse<>(errorMessage, mRes);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<OrganizationsResponse> organizationsCreateEndpoint(@Valid @RequestBody OrganizationsEditRequest requestBody,
                                                                               HttpServletRequest request, HttpServletResponse response) {
        if (requestBody.getName() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("Missing request body values.", null);
        }

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        UserEntity user = userService.getOneById(userId);
        if (user == null || !user.getEmailVerified()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The user's email is not verified.", null);
            // The user could also no longer exist but this is extremely unlikely.
        }

        OrganizationEntity newOrg = organizationService.create(requestBody.getName());
        if (newOrg == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The given organization parameters are not valid.", null);
        }

        UserOrganizationRelationshipEntity newUOR = uorService.create(user.getId(), newOrg.getId());
        if (newUOR == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new StandardResponse<>("Failed to connect the user and organization.", null);
        }

        OrganizationsResponse mRes = new OrganizationsResponse(newOrg.getId(), newOrg.getName());
        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, mRes);
    }

}
