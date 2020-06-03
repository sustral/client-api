package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardQueryRequest;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.files.FilesResponse;
import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.dataservices.FileService;
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
 * This controller handles the /files routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/files")
public class FilesController {

    private final AuthorizationHelper authorizationHelper;
    private final FileService fileService;

    @Autowired
    public FilesController(AuthorizationHelper authorizationHelper, FileService fileService) {
        this.authorizationHelper = authorizationHelper;
        this.fileService = fileService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<FilesResponse>> filesEndpoint(@Valid @RequestBody StandardQueryRequest requestBody,
                                                               HttpServletRequest request, HttpServletResponse response) {

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessFiles(userId, requestBody.getIds());

        List<FilesResponse> responseBody = new ArrayList<>();
        for (int i = 0; i < requestBody.getIds().length; i++) {
            if (authorizedToAccess[i]) {
                String[] idComponents = requestBody.getIds()[i].split("/");
                FileEntity tempFile = fileService.getOneById(idComponents[0], idComponents[1], idComponents[2]);
                if (tempFile != null) {
                    responseBody.add(new FilesResponse(tempFile.getFieldId() + "/" + tempFile.getScanId() + "/" + tempFile.getId(),
                            tempFile.getFileType().toString()));
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, responseBody);
    }

}
