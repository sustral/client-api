package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.ScanEntity;
import com.sustral.clientapi.data.models.ScanEntityPK;
import com.sustral.clientapi.data.repositories.ScanRepository;
import com.sustral.clientapi.data.types.ScanStatusE;
import com.sustral.clientapi.data.utils.CustomUUIDGenerator;
import com.sustral.clientapi.services.ScanService;
import com.sustral.clientapi.services.types.ServiceReturn;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * An implementation of ScanService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class ScanServiceImpl implements ScanService {

    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private CustomUUIDGenerator uuidGenerator;

    @Override
    public ServiceReturn<ScanEntity> getOneById(String fieldId, String scanId) {
        ScanEntityPK id = new ScanEntityPK();
        id.setFieldId(fieldId);
        id.setId(scanId);

        Optional<ScanEntity> scan = scanRepository.findById(id);

        if (scan.isPresent()) {
            return new ServiceReturn<>(false, null, null, scan.get());
        }

        String errorMessage = "The scan could not be found.";
        return new ServiceReturn<>(true, errorMessage, errorMessage, null);
    }

    @Override
    public ServiceReturn<List<ScanEntity>> getManyByFieldId(String fieldId) {
        List<ScanEntity> scans = scanRepository.findAllByFieldId(fieldId);

        return new ServiceReturn<>(false, null, null, scans);
    }

    @Override
    public ServiceReturn<ScanEntity> create(String fieldId, Polygon coordinates) {

        ScanEntity scan = new ScanEntity();

        scan.setFieldId(fieldId);

        String uuid = uuidGenerator.generateUUID();
        scan.setId(uuid);

        scan.setCoordinates(coordinates);

        scan.setScanStatus(ScanStatusE.PENDING_COLLECTION);

        ScanEntity updatedScan =  scanRepository.save(scan);
        return new ServiceReturn<>(false, null, null, updatedScan);
    }

}
