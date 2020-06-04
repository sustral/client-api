package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.StandardSinglePaginatedQueryRequest;
import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;
import com.sustral.clientapi.dataservices.FieldOrganizationRelationshipService;
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
 * This controller handles the /fields_to_organizations routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/fields_to_organizations")
public class FieldsToOrganizationsController {

    private final AuthorizationHelper authorizationHelper;
    private final FieldOrganizationRelationshipService forService;

    @Autowired
    public FieldsToOrganizationsController(AuthorizationHelper authorizationHelper, FieldOrganizationRelationshipService forService) {
        this.authorizationHelper = authorizationHelper;
        this.forService = forService;
    }

    @PostMapping(path = "/fields", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<String>> fieldsToOrganizationsFieldsEndpoint(@Valid @RequestBody StandardSinglePaginatedQueryRequest requestBody,
                                                                              HttpServletRequest request, HttpServletResponse response) {
        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessOrganizations(userId, Arrays.append(new String[] {}, requestBody.getId()));
        if (!authorizedToAccess[0]) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>("The user does not have access to this organization.", null);
        }

        List<FieldOrganizationRelationshipEntity> fors = forService.getManyByOrganizationId(requestBody.getId(), requestBody.getOffset(), requestBody.getLimit());

        List<String> fieldIds = fors.stream().map(FieldOrganizationRelationshipEntity::getFieldId).collect(Collectors.toList());

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, fieldIds);
    }

    @PostMapping(path = "/organizations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<String>> fieldsToOrganizationsOrganizationsEndpoint(@Valid @RequestBody StandardSinglePaginatedQueryRequest requestBody,
                                                                                     HttpServletRequest request, HttpServletResponse response) {
        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessFields(userId, Arrays.append(new String[] {}, requestBody.getId()));
        if (!authorizedToAccess[0]) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>("The user does not have access to this field.", null);
        }

        List<FieldOrganizationRelationshipEntity> fors = forService.getManyByFieldId(requestBody.getId(), requestBody.getOffset(), requestBody.getLimit());

        List<String> orgIds = fors.stream().map(FieldOrganizationRelationshipEntity::getOrganizationId).collect(Collectors.toList());

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, orgIds);
    }

}
