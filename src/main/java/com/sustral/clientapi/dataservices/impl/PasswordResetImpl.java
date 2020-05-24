package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.PasswordResetEntity;
import com.sustral.clientapi.data.repositories.PasswordResetRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.dataservices.PasswordResetService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
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

    private static final long SIX_HOURS = 6 * 60 * 60 * 1000; // Six hours in ms to use as a expiration cutoff

    private final PasswordResetRepository resetRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public PasswordResetImpl(PasswordResetRepository resetRepository, IdGenerator idGenerator) {
        this.resetRepository = resetRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public PasswordResetEntity findOneAndDeleteByToken(String token) {

        String hashedToken = DigestUtils.sha256Hex(token);

        Optional<PasswordResetEntity> reset = resetRepository.findById(hashedToken);

        if (reset.isEmpty()) {
            return null;
        }

        // Delete to prevent reuse
        resetRepository.delete(reset.get());

        // Expiration check
        long created = reset.get().getCreated().getTime();
        long cutoff = System.currentTimeMillis() - SIX_HOURS;

        if (created < cutoff) {
            return null;
        }

        return reset.get();
    }

    @Override
    public TokenWrapper<String, PasswordResetEntity> create(String userId) {

        String newId = idGenerator.generateId(); // To be used as the token sent to the user
        String hashedId = DigestUtils.sha256Hex(newId);

        PasswordResetEntity reset = new PasswordResetEntity();

        reset.setToken(hashedId);
        reset.setUserId(userId);

        PasswordResetEntity updatedReset = resetRepository.save(reset); // Guaranteed to not be null

        return new TokenWrapper<>(newId, updatedReset);
    }

}
