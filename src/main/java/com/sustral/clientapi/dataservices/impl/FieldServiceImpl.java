package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.FieldEntity;
import com.sustral.clientapi.data.repositories.FieldRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.dataservices.FieldService;
import com.sustral.clientapi.dataservices.types.NameValidation;
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

    private final FieldRepository fieldRepository;
    private final Validator validator;
    private final IdGenerator idGenerator;

    @Autowired
    public FieldServiceImpl(FieldRepository fieldRepository, Validator validator, IdGenerator idGenerator) {
        this.fieldRepository = fieldRepository;
        this.validator = validator;
        this.idGenerator = idGenerator;
    }

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
    public FieldEntity getOneById(String id) {
        Optional<FieldEntity> field = fieldRepository.findById(id);

        return field.orElse(null);
    }

    @Override
    public List<FieldEntity> getManyById(List<String> ids) {
        return fieldRepository.findAllById(ids); // Guaranteed by JPARepository to not be null, but may be empty
    }

    @Override
    public FieldEntity create(String name, Polygon coordinates) {

        if (validateName(name) < 0) {
            return null;
        }

        if (validateCoordinates(coordinates) < 0) {
            return null;
        }

        FieldEntity field = new FieldEntity();

        String newId = idGenerator.generateId();
        field.setId(newId);

        field.setName(name);

        field.setCoordinates(coordinates);

        field.setApproved(false);

        return fieldRepository.save(field); // Guaranteed to never be null
    }

    @Override
    public FieldEntity setName(FieldEntity field, String name) {

        if (validateName(name) < 0) {
            return null;
        }

        field.setName(name);
        return fieldRepository.save(field); // Guaranteed to never be null
    }

    @Override
    public FieldEntity setCoordinates(FieldEntity field, Polygon coordinates) {

        if (validateCoordinates(coordinates) < 0) {
            return null;
        }

        field.setCoordinates(coordinates);
        field.setApproved(false);
        return fieldRepository.save(field); // Guaranteed to never be null
    }

    @Override
    public FieldEntity setApproved(FieldEntity field, boolean approved) {
        field.setApproved(approved);
        return fieldRepository.save(field); // Guaranteed to never be null
    }

}
