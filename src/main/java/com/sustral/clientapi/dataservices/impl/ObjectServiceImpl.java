package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.objects.ObjectRepository;
import com.sustral.clientapi.dataservices.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * An implementation of ObjectService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class ObjectServiceImpl implements ObjectService {

    private final ObjectRepository objectRepository;

    @Autowired
    public ObjectServiceImpl(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    @Override
    public InputStream getOneById(String id) {
        return objectRepository.download(id);
    }

    @Override
    public int setOneById(String id, InputStream obj) {
        if (id == null || id.isBlank() || obj == null) {
            return -1;
        }

        int error = objectRepository.upload(id, obj);

        if (error < 0) {
            return -1;
        }

        return 0;
    }

}
