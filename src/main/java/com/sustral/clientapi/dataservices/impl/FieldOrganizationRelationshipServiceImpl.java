package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntityPK;
import com.sustral.clientapi.data.repositories.FieldOrganizationRelationshipRepository;
import com.sustral.clientapi.dataservices.FieldOrganizationRelationshipService;
import com.sustral.clientapi.utils.PaginationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * An implementation of FieldOrganizationRelationShipService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class FieldOrganizationRelationshipServiceImpl implements FieldOrganizationRelationshipService {

    private static final int PAGE_SIZE = 20;

    private final FieldOrganizationRelationshipRepository forRepository;

    @Autowired
    public FieldOrganizationRelationshipServiceImpl(FieldOrganizationRelationshipRepository forRepository) {
        this.forRepository = forRepository;
    }

    @Override
    public FieldOrganizationRelationshipEntity getOneById(String fieldId, String orgId) {
        FieldOrganizationRelationshipEntityPK id = new FieldOrganizationRelationshipEntityPK(fieldId, orgId);
        Optional<FieldOrganizationRelationshipEntity> fore = forRepository.findById(id);

        return fore.orElse(null);
    }

    @Override
    public List<FieldOrganizationRelationshipEntity> getManyByFieldId(String fieldId, int offset, int limit) {
        PaginationManager<FieldOrganizationRelationshipEntity> paginationManager = new PaginationManager<>(offset, limit, PAGE_SIZE);
        int[] pageIndices = paginationManager.getFirstAndLastPageIndices();

        for (int i = pageIndices[0]; i <= pageIndices[1]; i++) {
            List<FieldOrganizationRelationshipEntity> fors = forRepository.findAllByFieldId(fieldId, PageRequest.of(i, PAGE_SIZE));
            if (fors == null || fors.size() == 0) { break; }
            paginationManager.addPage(fors);
        }

        return paginationManager.getFinalResults(); // Guaranteed to not be null
    }

    @Override
    public List<FieldOrganizationRelationshipEntity> getManyByOrganizationId(String orgId, int offset, int limit) {
        PaginationManager<FieldOrganizationRelationshipEntity> paginationManager = new PaginationManager<>(offset, limit, PAGE_SIZE);
        int[] pageIndices = paginationManager.getFirstAndLastPageIndices();

        for (int i = pageIndices[0]; i <= pageIndices[1]; i++) {
            List<FieldOrganizationRelationshipEntity> fors = forRepository.findAllByOrganizationId(orgId, PageRequest.of(i, PAGE_SIZE));
            if (fors == null || fors.size() == 0) { break; }
            paginationManager.addPage(fors);
        }

        return paginationManager.getFinalResults(); // Guaranteed to not be null
    }

    @Override
    public FieldOrganizationRelationshipEntity create(String fieldId, String orgId) {

        FieldOrganizationRelationshipEntity fore = new FieldOrganizationRelationshipEntity();
        fore.setFieldId(fieldId);
        fore.setOrganizationId(orgId);

        return forRepository.save(fore); // Guaranteed to not be null
    }

}
