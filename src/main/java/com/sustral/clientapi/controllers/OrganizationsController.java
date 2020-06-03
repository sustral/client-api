package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardQueryRequest;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.organizations.OrganizationsResponse;
import com.sustral.clientapi.data.models.OrganizationEntity;
import com.sustral.clientapi.dataservices.OrganizationService;
import com.sustral.clientapi.dataservices.UserOrganizationRelationshipService;
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

}
