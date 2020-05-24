package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntityPK;
import com.sustral.clientapi.data.repositories.FieldOrganizationRelationshipRepository;
import com.sustral.clientapi.dataservices.FieldOrganizationRelationshipService;
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
    public List<FieldOrganizationRelationshipEntity> getManyByFieldId(String fieldId) {
        return forRepository.findAllByFieldId(fieldId); // Guaranteed to not be null
    }

    @Override
    public List<FieldOrganizationRelationshipEntity> getManyByOrganizationId(String orgId) {
        return forRepository.findAllByOrganizationId(orgId); // Guaranteed to not be null
    }

    @Override
    public FieldOrganizationRelationshipEntity create(String fieldId, String orgId) {

        FieldOrganizationRelationshipEntity fore = new FieldOrganizationRelationshipEntity();
        fore.setFieldId(fieldId);
        fore.setOrganizationId(orgId);

        return forRepository.save(fore); // Guaranteed to not be null
    }

}
