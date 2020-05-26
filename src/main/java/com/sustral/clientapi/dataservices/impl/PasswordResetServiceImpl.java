package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.PasswordResetEntity;
import com.sustral.clientapi.data.repositories.PasswordResetRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.dataservices.PasswordResetService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
import com.sustral.clientapi.utils.ConfigurationParser;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * An implementation of PasswordResetService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final long prExpiry;

    private final PasswordResetRepository resetRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public PasswordResetServiceImpl(PasswordResetRepository resetRepository, IdGenerator idGenerator,
                                    @Value("${sustral.security.passwordResetExpiration}") String prExpiryConfig) {
        this.resetRepository = resetRepository;
        this.idGenerator = idGenerator;
        this.prExpiry = ConfigurationParser.parseTime(prExpiryConfig);
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
        long cutoff = System.currentTimeMillis() - prExpiry;

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
