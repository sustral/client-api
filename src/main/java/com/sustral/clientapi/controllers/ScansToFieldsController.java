package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.StandardSinglePaginatedQueryRequest;
import com.sustral.clientapi.data.models.ScanEntity;
import com.sustral.clientapi.dataservices.ScanService;
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
 * This controller handles the /scans_to_fields routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/scans_to_fields")
public class ScansToFieldsController {

    private final AuthorizationHelper authorizationHelper;
    private final ScanService scanService;

    @Autowired
    public ScansToFieldsController(AuthorizationHelper authorizationHelper, ScanService scanService) {
        this.authorizationHelper = authorizationHelper;
        this.scanService = scanService;
    }

    @PostMapping(path = "/scans", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<String>> scansToFieldsScansEndpoint(@Valid @RequestBody StandardSinglePaginatedQueryRequest requestBody,
                                                                     HttpServletRequest request, HttpServletResponse response) {
        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessFields(userId, Arrays.append(new String[] {}, requestBody.getId()));
        if (Boolean.FALSE.equals(authorizedToAccess[0])) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>("The user does not have access to this field.", null);
        }

        List<ScanEntity> scans = scanService.getManyByFieldId(requestBody.getId(), requestBody.getOffset(), requestBody.getLimit());

        List<String> scanIds = scans.stream().map(s -> s.getFieldId() + "/" + s.getId()).collect(Collectors.toList());

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, scanIds);
    }

}
