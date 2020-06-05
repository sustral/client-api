package com.sustral.clientapi.temporaryrobotapi;

import com.sustral.clientapi.controllers.types.StandardResponse;
import com.sustral.clientapi.controllers.types.scans.ScansResponse;
import com.sustral.clientapi.data.models.FileEntity;
import com.sustral.clientapi.data.types.FileTypeE;
import com.sustral.clientapi.data.types.ScanStatusE;
import com.sustral.clientapi.dataservices.FileService;
import com.sustral.clientapi.dataservices.ObjectService;
import com.sustral.clientapi.dataservices.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.sustral.clientapi.data.models.ScanEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A temporary controller used for testing the robot.
 *
 * @author Dilanka Dharmasena
 */
@ConditionalOnProperty(name = "robotApi.enabled", havingValue = "true")
@RestController
@RequestMapping("/missions")
public class MissionsController {

    public final ScanService scanService;
    public final FileService fileService;
    public final ObjectService objectService;

    @Autowired
    public MissionsController(ScanService scanService, FileService fileService, ObjectService objectService) {
        this.scanService = scanService;
        this.fileService = fileService;
        this.objectService = objectService;
    }

    @PostMapping(path = "/request", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<ScansResponse> missionRequestEndpoint(HttpServletResponse response) {

        ScanEntity scan = scanService.getOldestOneByScanStatus(ScanStatusE.PENDING_COLLECTION);
        if (scan == null) {
            response.setStatus(HttpServletResponse.SC_OK);
            return new StandardResponse<>("There are no scans pending collection.", null);
        }

        scan = scanService.advanceState(scan);
        if (scan == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new StandardResponse<>("Failed to update the scan status.", null);
        }

        ScansResponse mRes = new ScansResponse(scan.getFieldId() + "/" + scan.getId(),
                scan.getUpdated().getTime(), scan.getScanStatus().toString(), scan.getCoordinates().toText());
        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, mRes);
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> missionUploadEndpoint(@RequestParam("file") MultipartFile rawObject,
                                                        @RequestHeader("Sustral-Scan") String scanId,
                                                        HttpServletResponse response) throws IOException {

        if (rawObject.getSize() < 10000) { // 10kb
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The given file is invalid", null);
        }

        String[] idComponents = scanId.split("/");

        ScanEntity scan = scanService.getOneById(idComponents[0], idComponents[1]);
        if (scan == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The scan does not exist.", null);
        }

        FileEntity file = fileService.create(idComponents[0], idComponents[1], FileTypeE.RGB_RAW);
        if (file == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new StandardResponse<>("Failed to create a new file.", null);
        }

        String objectId = fileService.getObjectId(file);
        InputStream object = new BufferedInputStream(rawObject.getInputStream(), 262144); // AWS SDK has a min recommended buffer size
        int uploadSuccess = objectService.setOneById(objectId, object);
        if (uploadSuccess < 0) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new StandardResponse<>("Failed to save the object.", null);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, null);
    }

    @PostMapping(path = "/end", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StandardResponse<Void> missionEndEndpoint(@RequestHeader("Sustral-Scan") String scanId,
                                                     HttpServletResponse response) {

        String[] idComponents = scanId.split("/");
        ScanEntity scan = scanService.getOneById(idComponents[0], idComponents[1]);
        if (scan == null || scan.getScanStatus() != ScanStatusE.COLLECTION) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StandardResponse<>("The scan does not exist or is past the collection stage.", null);
        }

        scanService.advanceState(scan);

        response.setStatus(HttpServletResponse.SC_OK);
        return new StandardResponse<>(null, null);
    }

}
