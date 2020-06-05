package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.ScanEntity;
import com.sustral.clientapi.data.models.ScanEntityPK;
import com.sustral.clientapi.data.repositories.ScanRepository;
import com.sustral.clientapi.data.types.ScanStatusE;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.dataservices.ScanService;
import com.sustral.clientapi.utils.PaginationManager;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    private static final int PAGE_SIZE = 20;

    private final ScanRepository scanRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public ScanServiceImpl(ScanRepository scanRepository, IdGenerator idGenerator) {
        this.scanRepository = scanRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public ScanEntity getOneById(String fieldId, String scanId) {
        ScanEntityPK id = new ScanEntityPK(scanId, fieldId);
        Optional<ScanEntity> scan = scanRepository.findById(id);

        return scan.orElse(null);
    }

    @Override
    public List<ScanEntity> getManyByFieldId(String fieldId, int offset, int limit) {
        PaginationManager<ScanEntity> paginationManager = new PaginationManager<>(offset, limit, PAGE_SIZE);
        int[] pageIndices = paginationManager.getFirstAndLastPageIndices();

        for (int i = pageIndices[0]; i <= pageIndices[1]; i++) {
            List<ScanEntity> scans = scanRepository.findAllByFieldId(fieldId, PageRequest.of(i, PAGE_SIZE));
            if (scans == null || scans.isEmpty()) { break; }
            paginationManager.addPage(scans);
        }

        return paginationManager.getFinalResults(); // Guaranteed to not be null
    }

    @Override
    public ScanEntity create(String fieldId, Polygon coordinates) {

        ScanEntity scan = new ScanEntity();

        scan.setFieldId(fieldId);

        String newId = idGenerator.generateId();
        scan.setId(newId);

        scan.setCoordinates(coordinates);

        scan.setScanStatus(ScanStatusE.initial());

        return scanRepository.save(scan); // Guaranteed to not be null
    }

    @Override
    public ScanEntity advanceState(ScanEntity scan) {

        ScanStatusE next = scan.getScanStatus().next();
        scan.setScanStatus(next);

        return scanRepository.save(scan);
    }

}
