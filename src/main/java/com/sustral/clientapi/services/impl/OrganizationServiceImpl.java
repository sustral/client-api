package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.OrganizationEntity;
import com.sustral.clientapi.data.repositories.OrganizationRepository;
import com.sustral.clientapi.services.OrganizationService;
import com.sustral.clientapi.services.types.NameValidation;
import com.sustral.clientapi.services.types.ServiceReturn;
import com.sustral.clientapi.data.utils.CustomUUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An implementation of OrganizationService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private CustomUUIDGenerator uuidGenerator;

    /**
     * Validates a passed in name based on constraints in NameValidation.
     *
     * This method is identical to validateName in FieldServiceImpl and UserServiceImpl.
     *
     * @param name  a string name
     * @return      an int; 0 for valid, -1 for invalid
     */
    private int validateName(String name) {
        NameValidation nameValidation = new NameValidation(name);
        Set<ConstraintViolation<NameValidation>> constraintViolations = validator.validate(nameValidation);
        if(constraintViolations.size() > 0 ) { return -1; }
        return 0;
    }

    @Override
    public ServiceReturn<OrganizationEntity> getOneById(String id) {
        Optional<OrganizationEntity> org = organizationRepository.findById(id);

        if (org.isPresent()) {
            return new ServiceReturn<OrganizationEntity>(false, null, null, org.get());
        }

        String errorMessage = "The organization could not be found by id.";
        String passthroughMessage = "The organization could not be found.";
        return new ServiceReturn<OrganizationEntity>(true, errorMessage, passthroughMessage, null);
    }

    @Override
    public ServiceReturn<List<OrganizationEntity>> getManyById(List<String> ids) {
        List<OrganizationEntity> orgs = organizationRepository.findAllById(ids); // Guaranteed by JPARepository to not be null, but may be empty

        return new ServiceReturn<List<OrganizationEntity>>(false, null, null, orgs);
    }

    @Override
    public ServiceReturn<OrganizationEntity> create(String name) {

        if (validateName(name) < 0) {
            String errorMessage = "The given name does not meet requirements.";
            return new ServiceReturn<OrganizationEntity>(true, errorMessage, errorMessage, null);
        }

        OrganizationEntity org = new OrganizationEntity();

        String uuid = uuidGenerator.generateUUID();
        org.setId(uuid);

        org.setName(name);

        OrganizationEntity updatedOrg = organizationRepository.save(org); // Guaranteed to never be null
        return new ServiceReturn<OrganizationEntity>(false, null, null, updatedOrg);
    }

    @Override
    public ServiceReturn<OrganizationEntity> setName(OrganizationEntity org, String name) {

        if (validateName(name) < 0) {
            String errorMessage = "The given name does not meet requirements.";
            return new ServiceReturn<OrganizationEntity>(true, errorMessage, errorMessage, null);
        }

        org.setName(name);
        OrganizationEntity updatedOrg = organizationRepository.save(org); // Guaranteed to never be null
        return new ServiceReturn<OrganizationEntity>(false, null, null, updatedOrg);
    }

}
