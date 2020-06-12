package com.sustral.clientapi.controllers;

import com.sustral.clientapi.controllers.helpers.AuthorizationHelper;
import com.sustral.clientapi.controllers.helpers.ClaimsRetrievalHelper;
import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.StandardSinglePaginatedQueryRequest;
import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.data.types.FileTypeE;
import com.sustral.clientapi.dataservices.FileService;
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
 * This controller handles the /files_to_scans routes.
 *
 * @author Dilanka Dharmasena
 */
@RestController
@RequestMapping("/files_to_scans")
public class FilesToScansController {

    private final AuthorizationHelper authorizationHelper;
    private final FileService fileService;

    @Autowired
    public FilesToScansController(AuthorizationHelper authorizationHelper, FileService fileService) {
        this.authorizationHelper = authorizationHelper;
        this.fileService = fileService;
    }

    @PostMapping(path = "/files/rgb_orthomosaics", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<List<String>> filesToScansFilesRgbOrthomosaicsEndpoint(@Valid @RequestBody StandardSinglePaginatedQueryRequest requestBody,
                                                                    HttpServletRequest request, HttpServletResponse response) {

        String userId = new ClaimsRetrievalHelper(request).getUserId();
        Boolean[] authorizedToAccess = authorizationHelper.canAccessScans(userId, Arrays.append(new String[] {}, requestBody.getId()));
        if (Boolean.FALSE.equals(authorizedToAccess[0])) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new StandardResponse<>("The user does not have access to this scan.", null);
        }

        String[] idComponents = requestBody.getId().split("/");
        List<FileEntity> files = fileService.getManyByScanIdAndFileType(idComponents[0], idComponents[1], FileTypeE.RGB_ORTHOMOSAIC, requestBody.getOffset(), requestBody.getLimit());

        List<String> fileIds = files.stream().map(s -> s.getFieldId() + "/" + s.getScanId() + "/" + s.getId()).collect(Collectors.toList());

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, fileIds);
    }

}
