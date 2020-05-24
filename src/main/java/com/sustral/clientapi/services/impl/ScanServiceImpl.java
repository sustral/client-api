package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.ScanEntity;
import com.sustral.clientapi.data.models.ScanEntityPK;
import com.sustral.clientapi.data.repositories.ScanRepository;
import com.sustral.clientapi.data.types.ScanStatusE;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.services.ScanService;
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
    private IdGenerator idGenerator;

    @Override
    public ScanEntity getOneById(String fieldId, String scanId) {
        ScanEntityPK id = new ScanEntityPK(scanId, fieldId);
        Optional<ScanEntity> scan = scanRepository.findById(id);

        return scan.orElse(null);
    }

    @Override
    public List<ScanEntity> getManyByFieldId(String fieldId) {
        return scanRepository.findAllByFieldId(fieldId); // Guaranteed to not be null
    }

    @Override
    public ScanEntity create(String fieldId, Polygon coordinates) {

        ScanEntity scan = new ScanEntity();

        scan.setFieldId(fieldId);

        String newId = idGenerator.generateId();
        scan.setId(newId);

        scan.setCoordinates(coordinates);

        scan.setScanStatus(ScanStatusE.PENDING_COLLECTION);

        return scanRepository.save(scan); // Guaranteed to not be null
    }

}
