package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardSingleQueryRequest;
import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.dataservices.FileService;
import com.sustral.clientapi.dataservices.ObjectService;
import org.apache.commons.io.IOUtils;
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
import java.io.IOException;
import java.io.InputStream;

/**
 * This controller handles the /objects routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/objects")
public class ObjectsController {

    private final AuthorizationHelper authorizationHelper;
    private final ObjectService objectService;
    private final FileService fileService;

    @Autowired
    public ObjectsController(AuthorizationHelper authorizationHelper, ObjectService objectService, FileService fileService) {
        this.authorizationHelper = authorizationHelper;
        this.objectService = objectService;
        this.fileService = fileService;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] objectsEndpoint(@Valid @RequestBody StandardSingleQueryRequest requestBody,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessFiles(userId, Arrays.append(new String[] {}, requestBody.getId()));

        if (!authorizedToAccess[0]) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new byte[] {};
        }

        String[] idComponents = requestBody.getId().split("/");
        FileEntity tempFile = fileService.getOneById(idComponents[0], idComponents[1], idComponents[2]);
        if (tempFile == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new byte[] {};
        }
        String objectId = fileService.getObjectId(tempFile);

        InputStream object = objectService.getOneById(objectId);
        if (object == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new byte[] {};
        }

        byte[] returnArray = IOUtils.toByteArray(object);
        object.close();

        return returnArray;
    }

}
