package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.PasswordResetEntity;
import com.sustral.clientapi.data.repositories.PasswordResetRepository;
import com.sustral.clientapi.data.utils.CustomUUIDGenerator;
import com.sustral.clientapi.services.PasswordResetService;
import com.sustral.clientapi.services.types.ServiceReturn;
import com.sustral.clientapi.services.types.TokenWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * An implementation of PasswordResetService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class PasswordResetImpl implements PasswordResetService {

    private static final int SIX_HOURS = 6 * 60 * 60 * 1000; // Six hours in ms to use as a expiration cutoff

    @Autowired
    private PasswordResetRepository resetRepository;

    @Autowired
    private CustomUUIDGenerator uuidGenerator;

    @Override
    public ServiceReturn<PasswordResetEntity> findOneAndDeleteByToken(String token) {

        String hashedToken = DigestUtils.sha256Hex(token);

        Optional<PasswordResetEntity> reset = resetRepository.findById(hashedToken);

        if (reset.isEmpty()) {
            return new ServiceReturn<>("E0000", null);
        }

        // Expiration check
        long created = reset.get().getCreated().getTime();
        long cutoff = System.currentTimeMillis() - SIX_HOURS;

        if (created < cutoff) {
            resetRepository.delete(reset.get());
            return new ServiceReturn<>("E0000", null);
        }

        // Delete to prevent reuse
        resetRepository.delete(reset.get());

        return new ServiceReturn<>(null, reset.get());
    }

    @Override
    public ServiceReturn<TokenWrapper<String, PasswordResetEntity>> create(String userId) {

        String uuid = uuidGenerator.generateUUID(); // To be used as the token sent to the user
        String hashedUuid = DigestUtils.sha256Hex(uuid);

        PasswordResetEntity reset = new PasswordResetEntity();

        reset.setToken(hashedUuid);
        reset.setUserId(userId);

        PasswordResetEntity updatedReset = resetRepository.save(reset);

        TokenWrapper<String, PasswordResetEntity> tw = new TokenWrapper<>(uuid, updatedReset);

        return new ServiceReturn<>(null, tw);
    }

}
