package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntityPK;
import com.sustral.clientapi.data.repositories.UserOrganizationRelationshipRepository;
import com.sustral.clientapi.services.UserOrganizationRelationshipService;
import com.sustral.clientapi.services.types.ServiceReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * An implementation of UserOrganizationRelationshipService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class UserOrganizationRelationshipImpl implements UserOrganizationRelationshipService {

    @Autowired
    private UserOrganizationRelationshipRepository uorRepository;

    @Override
    public ServiceReturn<UserOrganizationRelationshipEntity> getOneById(String userId, String orgId) {
        UserOrganizationRelationshipEntityPK id = new UserOrganizationRelationshipEntityPK();
        id.setUserId(userId);
        id.setOrganizationId(orgId);

        Optional<UserOrganizationRelationshipEntity> uor = uorRepository.findById(id);

        if (uor.isPresent()) {
            return new ServiceReturn<>(null, uor.get());
        }

        return new ServiceReturn<>("E0000", null);
    }

    @Override
    public ServiceReturn<List<UserOrganizationRelationshipEntity>> getManyByUserId(String userId) {
        List<UserOrganizationRelationshipEntity> uors = uorRepository.findAllByUserId(userId); // Guaranteed not null
        return new ServiceReturn<>(null, uors);
    }

    @Override
    public ServiceReturn<List<UserOrganizationRelationshipEntity>> getManyByOrganizationId(String orgId) {
        List<UserOrganizationRelationshipEntity> uors = uorRepository.findAllByOrganizationId(orgId); // Guaranteed not null
        return new ServiceReturn<>(null, uors);
    }

    @Override
    public ServiceReturn<UserOrganizationRelationshipEntity> create(String userId, String orgId) {

        UserOrganizationRelationshipEntity uor = new UserOrganizationRelationshipEntity();
        uor.setUserId(userId);
        uor.setOrganizationId(orgId);

        UserOrganizationRelationshipEntity updatedUor = uorRepository.save(uor); // Guaranteed to not be null
        return new ServiceReturn<>(null, updatedUor);
    }

}
