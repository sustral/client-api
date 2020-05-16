package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.objects.ObjectRepository;
import com.sustral.clientapi.services.ObjectService;
import com.sustral.clientapi.services.types.ServiceReturn;
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

    @Autowired
    private ObjectRepository objectRepository;

    @Override
    public ServiceReturn<InputStream> getOneById(String id) {
        InputStream obj = objectRepository.download(id);

        if (obj == null) {
            return new ServiceReturn<>("E0000", null);
        }

        return new ServiceReturn<>(null, obj);
    }

    @Override
    public ServiceReturn<Void> setOneById(String id, InputStream obj) {
        if (id.isBlank() || (obj == null)) {
            return new ServiceReturn<>("E0000", null);
        }

        int error = objectRepository.upload(id, obj);

        if (error < 0) {
            return new ServiceReturn<>("E0000", null);
        }

        return new ServiceReturn<>(null, null);
    }

}
