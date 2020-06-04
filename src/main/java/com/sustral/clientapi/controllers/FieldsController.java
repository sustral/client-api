package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardEditRequest;
import com.sustral.clientapi.controllers.types.StandardQueryRequest;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.fields.FieldsCreateRequest;
import com.sustral.clientapi.controllers.types.fields.FieldsEditRequest;
import com.sustral.clientapi.controllers.types.fields.FieldsResponse;
import com.sustral.clientapi.data.models.FieldEntity;
import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.FieldOrganizationRelationshipService;
import com.sustral.clientapi.dataservices.FieldService;
import com.sustral.clientapi.dataservices.UserService;
import org.bouncycastle.util.Arrays;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
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
    private final WKTReader wktReader;

    @Autowired
    public FieldsController(AuthorizationHelper authorizationHelper, UserService userService,
                            FieldService fieldService, FieldOrganizationRelationshipService forService) {
        this.authorizationHelper = authorizationHelper;
        this.userService = userService;
        this.fieldService = fieldService;
        this.forService = forService;
        this.wktReader = new WKTReader();
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<FieldsResponse>> fieldsEndpoint(@Valid @RequestBody StandardQueryRequest requestBody,
                                                                 HttpServletRequest request, HttpServletResponse response) {

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessFields(userId, requestBody.getIds());

        List<FieldsResponse> responseBody = new ArrayList<>();
        for (int i = 0; i < requestBody.getIds().length; i++) {
            if (Boolean.TRUE.equals(authorizedToAccess[i])) {
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

    @PostMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<FieldsResponse> fieldsEditEndpoint(@Valid @RequestBody StandardEditRequest<FieldsEditRequest> requestBody,
                                                               HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String errorMessage = "";

        String userId = new ClaimsRetrievalHelper(request).getUserId();

        Boolean[] authorizedToAccess = authorizationHelper.canAccessFields(userId, Arrays.append(new String[] {}, requestBody.getId()));
        if (Boolean.FALSE.equals(authorizedToAccess[0])) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>("The user does not have access to this field.", null);
        }

        UserEntity user = userService.getOneById(userId);
        if (user == null || !user.getEmailVerified()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The user's email is not verified.", null);
            // The user could also no longer exist but this is extremely unlikely.
        }

        FieldEntity field = fieldService.getOneById(requestBody.getId());
        if (field == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The field does not exist.", null);
        }

        if (requestBody.getData().getName() != null && !requestBody.getData().getName().isBlank()) {
            FieldEntity updatedField = fieldService.setName(field, requestBody.getData().getName());
            if (updatedField == null) {
                response.setStatus(207);
                errorMessage += "Failed to change name. ";
            } else {
                field = updatedField;
            }
        }

        if (requestBody.getData().getCoordinates() != null && !requestBody.getData().getCoordinates().isBlank()) {
            FieldEntity updatedField = fieldService.setCoordinates(field, (Polygon) wktReader.read(requestBody.getData().getCoordinates()));
            if (updatedField == null) {
                response.setStatus(207);
                errorMessage += "Failed to change coordinates. ";
            } else {
                field = updatedField;
            }
        }

        FieldsResponse mRes = new FieldsResponse(field.getId(), field.getName(), field.getCoordinates().toText(), field.getApproved());
        if (response.getStatus() != 207) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return new StandardResponse<>(errorMessage, mRes);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<FieldsResponse> fieldsCreateEndpoint(@Valid @RequestBody FieldsCreateRequest requestBody,
                                                                 HttpServletRequest request, HttpServletResponse response) throws ParseException {

        String userId = new ClaimsRetrievalHelper(request).getUserId();

        Boolean[] authorizedToAccess = authorizationHelper.canAccessOrganizations(userId, Arrays.append(new String[] {}, requestBody.getOrganization()));
        if (Boolean.FALSE.equals(authorizedToAccess[0])) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>("The user does not have access to this organization.", null);
        }

        UserEntity user = userService.getOneById(userId);
        if (user == null || !user.getEmailVerified()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The user's email is not verified.", null);
            // The user could also no longer exist but this is extremely unlikely.
        }

        FieldEntity newField = fieldService.create(requestBody.getName(), (Polygon) wktReader.read(requestBody.getCoordinates()));
        if (newField ==  null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The given field parameters are not valid.", null);
        }

        FieldOrganizationRelationshipEntity newFOR = forService.create(newField.getId(), requestBody.getOrganization());
        if (newFOR == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new StandardResponse<>("Failed to connect the field and organization.", null);
        }

        FieldsResponse mRes = new FieldsResponse(newField.getId(), newField.getName(), newField.getCoordinates().toText(), newField.getApproved());
        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, mRes);
    }

}
