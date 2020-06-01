package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntityPK;
import com.sustral.clientapi.data.repositories.UserOrganizationRelationshipRepository;
import com.sustral.clientapi.dataservices.UserOrganizationRelationshipService;
import com.sustral.clientapi.utils.PaginationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An implementation of UserOrganizationRelationshipService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class UserOrganizationRelationshipServiceImpl implements UserOrganizationRelationshipService {

    private static final int PAGE_SIZE = 20;

    private final UserOrganizationRelationshipRepository uorRepository;

    @Autowired
    public UserOrganizationRelationshipServiceImpl(UserOrganizationRelationshipRepository uorRepository) {
        this.uorRepository = uorRepository;
    }

    @Override
    public UserOrganizationRelationshipEntity getOneById(String userId, String orgId) {
        UserOrganizationRelationshipEntityPK id = new UserOrganizationRelationshipEntityPK(userId, orgId);
        Optional<UserOrganizationRelationshipEntity> uor = uorRepository.findById(id);

        return uor.orElse(null);
    }

    @Override
    public List<UserOrganizationRelationshipEntity> getManyByUserId(String userId, int offset, int limit) {
        PaginationManager<UserOrganizationRelationshipEntity> paginationManager = new PaginationManager<>(offset, limit, PAGE_SIZE);
        int[] pageIndices = paginationManager.getFirstAndLastPageIndices();

        for (int i = pageIndices[0]; i <= pageIndices[1]; i++) {
            List<UserOrganizationRelationshipEntity> uors = uorRepository.findAllByUserId(userId, PageRequest.of(i, PAGE_SIZE));
            if (uors == null || uors.isEmpty()) { break; }
            paginationManager.addPage(uors);
        }

        return paginationManager.getFinalResults(); // Guaranteed to not be null
    }

    @Override
    public List<UserOrganizationRelationshipEntity> getManyByUserId(String userId) {
        List<UserOrganizationRelationshipEntity> uors = new ArrayList<>();
        boolean continueRetrieval = true;
        int currentIndex = 0;

        while (continueRetrieval) {
            List<UserOrganizationRelationshipEntity> tempUors = getManyByUserId(userId, currentIndex, currentIndex + PAGE_SIZE);
            uors.addAll(tempUors);
            currentIndex += PAGE_SIZE;
            if (tempUors.size() < PAGE_SIZE) { continueRetrieval = false; }
        }

        return uors;
    }

    @Override
    public List<UserOrganizationRelationshipEntity> getManyByOrganizationId(String orgId, int offset, int limit) {
        PaginationManager<UserOrganizationRelationshipEntity> paginationManager = new PaginationManager<>(offset, limit, PAGE_SIZE);
        int[] pageIndices = paginationManager.getFirstAndLastPageIndices();

        for (int i = pageIndices[0]; i <= pageIndices[1]; i++) {
            List<UserOrganizationRelationshipEntity> uors = uorRepository.findAllByOrganizationId(orgId, PageRequest.of(i, PAGE_SIZE));
            if (uors == null || uors.isEmpty()) { break; }
            paginationManager.addPage(uors);
        }

        return paginationManager.getFinalResults(); // Guaranteed to not be null
    }

    @Override
    public List<UserOrganizationRelationshipEntity> getManyByOrganizationId(String orgId) {
        List<UserOrganizationRelationshipEntity> uors = new ArrayList<>();
        boolean continueRetrieval = true;
        int currentIndex = 0;

        while (continueRetrieval) {
            List<UserOrganizationRelationshipEntity> tempUors = getManyByOrganizationId(orgId, currentIndex, currentIndex + PAGE_SIZE);
            uors.addAll(tempUors);
            currentIndex += PAGE_SIZE;
            if (tempUors.size() < PAGE_SIZE) { continueRetrieval = false; }
        }

        return uors;
    }

    @Override
    public UserOrganizationRelationshipEntity create(String userId, String orgId) {

        UserOrganizationRelationshipEntity uor = new UserOrganizationRelationshipEntity();
        uor.setUserId(userId);
        uor.setOrganizationId(orgId);

        return uorRepository.save(uor); // Guaranteed to not be null
    }

}
