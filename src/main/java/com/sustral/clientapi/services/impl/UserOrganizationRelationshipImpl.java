package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntityPK;
import com.sustral.clientapi.data.repositories.UserOrganizationRelationshipRepository;
import com.sustral.clientapi.services.UserOrganizationRelationshipService;
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
    public UserOrganizationRelationshipEntity getOneById(String userId, String orgId) {
        UserOrganizationRelationshipEntityPK id = new UserOrganizationRelationshipEntityPK(userId, orgId);
        Optional<UserOrganizationRelationshipEntity> uor = uorRepository.findById(id);

        return uor.orElse(null);
    }

    @Override
    public List<UserOrganizationRelationshipEntity> getManyByUserId(String userId) {
        return uorRepository.findAllByUserId(userId); // Guaranteed not null
    }

    @Override
    public List<UserOrganizationRelationshipEntity> getManyByOrganizationId(String orgId) {
        return uorRepository.findAllByOrganizationId(orgId); // Guaranteed not null
    }

    @Override
    public UserOrganizationRelationshipEntity create(String userId, String orgId) {

        UserOrganizationRelationshipEntity uor = new UserOrganizationRelationshipEntity();
        uor.setUserId(userId);
        uor.setOrganizationId(orgId);

        return uorRepository.save(uor); // Guaranteed to not be null
    }

}
