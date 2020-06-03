package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardQueryRequest;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.fields.FieldsResponse;
import com.sustral.clientapi.data.models.FieldEntity;
import com.sustral.clientapi.dataservices.FieldOrganizationRelationshipService;
import com.sustral.clientapi.dataservices.FieldService;
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
 * This controller handles the /fields routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/fields")
public class FieldsController {

    private final AuthorizationHelper authorizationHelper;
    private final UserService userService;
    private final FieldService fieldService;
    private final FieldOrganizationRelationshipService forService;

    @Autowired
    public FieldsController(AuthorizationHelper authorizationHelper, UserService userService,
                            FieldService fieldService, FieldOrganizationRelationshipService forService) {
        this.authorizationHelper = authorizationHelper;
        this.userService = userService;
        this.fieldService = fieldService;
        this.forService = forService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<FieldsResponse>> fieldsEndpoint(@Valid @RequestBody StandardQueryRequest requestBody,
                                                                 HttpServletRequest request, HttpServletResponse response) {

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessFields(userId, requestBody.getIds());

        List<FieldsResponse> responseBody = new ArrayList<>();
        for (int i = 0; i < requestBody.getIds().length; i++) {
            if (authorizedToAccess[i]) {
                FieldEntity tempField = fieldService.getOneById(requestBody.getIds()[i]);
                if (tempField != null) {
                    responseBody.add(new FieldsResponse(tempField.getId(), tempField.getName(),
                            tempField.getCoordinates().toText(), tempField.getApproved()));
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, responseBody);
    }

}
