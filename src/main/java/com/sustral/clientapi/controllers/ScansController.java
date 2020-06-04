package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardQueryRequest;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.scans.ScansResponse;
import com.sustral.clientapi.data.models.ScanEntity;
import com.sustral.clientapi.dataservices.ScanService;
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
 * This controller handles the /scans routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/scans")
public class ScansController {

    private final AuthorizationHelper authorizationHelper;
    private final ScanService scanService;

    @Autowired
    public ScansController(AuthorizationHelper authorizationHelper, ScanService scanService) {
        this.authorizationHelper = authorizationHelper;
        this.scanService = scanService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<ScansResponse>> scansEndpoint(@Valid @RequestBody StandardQueryRequest requestBody,
                                                                       HttpServletRequest request, HttpServletResponse response) {

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessScans(userId, requestBody.getIds());

        List<ScansResponse> responseBody = new ArrayList<>();
        for (int i = 0; i < requestBody.getIds().length; i++) {
            if (Boolean.TRUE.equals(authorizedToAccess[i])) {
                String[] idComponents = requestBody.getIds()[i].split("/");
                ScanEntity tempScan = scanService.getOneById(idComponents[0], idComponents[1]);
                if (tempScan != null) {
                    responseBody.add(new ScansResponse(tempScan.getFieldId() + "/" + tempScan.getId(), tempScan.getUpdated().getTime(),
                            tempScan.getScanStatus().toString(), tempScan.getCoordinates().toText()));
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, responseBody);
    }

}
