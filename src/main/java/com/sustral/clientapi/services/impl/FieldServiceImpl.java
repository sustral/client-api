package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.FieldEntity;
import com.sustral.clientapi.data.repositories.FieldRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.services.FieldService;
import com.sustral.clientapi.services.types.NameValidation;
import com.sustral.clientapi.services.types.ServiceReturn;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An implementation of FieldService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * Validates a passed in name based on constraints in NameValidation.
     *
     * This method is identical to validateName in UserServiceImpl and OrganizationServiceImpl.
     *
     * @param name  a string name
     * @return      an int; 0 for valid, -1 for invalid
     */
    private int validateName(String name) {
        NameValidation nameValidation = new NameValidation(name);
        Set<ConstraintViolation<NameValidation>> constraintViolations = validator.validate(nameValidation);
        if(!constraintViolations.isEmpty()) { return -1; }
        return 0;
    }

    /**
     * Validates passed in coordinates.
     *
     * @param coordinates   a JTS Polygon
     * @return              an int; 0 for valid, -1 invalid
     */
    private int validateCoordinates(Polygon coordinates) {
        // This is just a superficial check
        // Every coordinates change (at creation or otherwise) will be manually checked

        return coordinates.isValid() ? 0 : -1;
    }

    @Override
    public ServiceReturn<FieldEntity> getOneById(String id) {
        Optional<FieldEntity> field = fieldRepository.findById(id);

        if (field.isPresent()) {
            return new ServiceReturn<>(null, field.get());
        }

        return new ServiceReturn<>("E0000", null);
    }

    @Override
    public ServiceReturn<List<FieldEntity>> getManyById(List<String> ids) {
        List<FieldEntity> fields = fieldRepository.findAllById(ids);

        return new ServiceReturn<>(null, fields);
    }

    @Override
    public ServiceReturn<FieldEntity> create(String name, Polygon coordinates) {

        if (validateName(name) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        if (validateCoordinates(coordinates) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        FieldEntity field = new FieldEntity();

        String newId = idGenerator.generateId();
        field.setId(newId);

        field.setName(name);

        field.setCoordinates(coordinates);

        field.setApproved(false);

        FieldEntity updatedField = fieldRepository.save(field);
        return new ServiceReturn<>(null, updatedField);
    }

    @Override
    public ServiceReturn<FieldEntity> setName(FieldEntity field, String name) {

        if (validateName(name) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        field.setName(name);
        FieldEntity updatedField = fieldRepository.save(field); // Guaranteed to never be null
        return new ServiceReturn<>(null, updatedField);
    }

    @Override
    public ServiceReturn<FieldEntity> setCoordinates(FieldEntity field, Polygon coordinates) {

        if (validateCoordinates(coordinates) < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        field.setCoordinates(coordinates);
        field.setApproved(false);
        FieldEntity updatedField = fieldRepository.save(field);
        return new ServiceReturn<>(null, updatedField);
    }

    @Override
    public ServiceReturn<FieldEntity> setApproved(FieldEntity field, boolean approved) {
        field.setApproved(approved);
        FieldEntity updatedField = fieldRepository.save(field);
        return new ServiceReturn<>(null, updatedField);
    }

}
