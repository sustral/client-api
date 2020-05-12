package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntityPK;
import com.sustral.clientapi.data.repositories.FieldOrganizationRelationshipRepository;
import com.sustral.clientapi.services.FieldOrganizationRelationshipService;
import com.sustral.clientapi.services.types.ServiceReturn;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FieldOrganizationRelationshipRepository forRepository;

    @Override
    public ServiceReturn<FieldOrganizationRelationshipEntity> getOneById(String fieldId, String orgId) {
        FieldOrganizationRelationshipEntityPK id = new FieldOrganizationRelationshipEntityPK();
        id.setFieldId(fieldId);
        id.setOrganizationId(orgId);

        Optional<FieldOrganizationRelationshipEntity> fore = forRepository.findById(id);

        if (fore.isPresent()) {
            return new ServiceReturn<FieldOrganizationRelationshipEntity>(false, null, null, fore.get());
        }

        String errorMessage = "The FOR could not be found by id";
        return new ServiceReturn<FieldOrganizationRelationshipEntity>(true, errorMessage, errorMessage, null);
    }

    @Override
    public ServiceReturn<List<FieldOrganizationRelationshipEntity>> getManyByFieldId(String fieldId) {
        List<FieldOrganizationRelationshipEntity> fors = forRepository.findAllByFieldId(fieldId); // Guaranteed to not be null
        return new ServiceReturn<List<FieldOrganizationRelationshipEntity>>(false, null, null, fors);
    }

    @Override
    public ServiceReturn<List<FieldOrganizationRelationshipEntity>> getManyByOrganizationId(String orgId) {
        List<FieldOrganizationRelationshipEntity> fors = forRepository.findAllByOrganizationId(orgId); // Guaranteed to not be null
        return new ServiceReturn<List<FieldOrganizationRelationshipEntity>>(false, null, null, fors);
    }

    @Override
    public ServiceReturn<FieldOrganizationRelationshipEntity> create(String fieldId, String orgId) {

        FieldOrganizationRelationshipEntity fore = new FieldOrganizationRelationshipEntity();
        fore.setFieldId(fieldId);
        fore.setOrganizationId(orgId);

        FieldOrganizationRelationshipEntity updatedFore = forRepository.save(fore); // Guaranteed to not be null

        return new ServiceReturn<FieldOrganizationRelationshipEntity>(false, null, null, updatedFore);
    }

}
