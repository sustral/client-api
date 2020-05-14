package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.EmailConfirmationEntity;
import com.sustral.clientapi.data.repositories.EmailConfirmationRepository;
import com.sustral.clientapi.data.utils.CustomUUIDGenerator;
import com.sustral.clientapi.services.EmailConfirmationService;
import com.sustral.clientapi.services.types.ServiceReturn;
import com.sustral.clientapi.services.types.TokenWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * An implementation of EmailConfirmationService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    private static final int ONE_DAY = 24 * 60 * 60 * 1000; // One day in ms to be used as a cutoff

    @Autowired
    private EmailConfirmationRepository ecRepository;

    @Autowired
    private CustomUUIDGenerator uuidGenerator;

    @Override
    public ServiceReturn<EmailConfirmationEntity> getOneAndDeleteByToken(String token) {
        String hashedToken = DigestUtils.sha256Hex(token);

        Optional<EmailConfirmationEntity> confirm = ecRepository.findById(hashedToken);

        if (confirm.isEmpty()) {
            String errorMessage = "The email confirmation token could not be found";
            return new ServiceReturn<>(true, errorMessage, errorMessage, null);
        }

        // Check if the token is expired
        long created = confirm.get().getCreated().getTime();
        long cutoff = System.currentTimeMillis() - ONE_DAY;

        if (created < cutoff) {
            String errorMessage = "The email confirmation token has expired";
            return new ServiceReturn<>(true, errorMessage, errorMessage, null);
        }

        // Delete the used token

        ecRepository.delete(confirm.get());

        return new ServiceReturn<>(false, null, null, confirm.get());
    }

    @Override
    public ServiceReturn<TokenWrapper<String, EmailConfirmationEntity>> create(String userId, String email) {

        String uuid = uuidGenerator.generateUUID();
        String hashedUuid = DigestUtils.sha256Hex(uuid);

        EmailConfirmationEntity confirm = new EmailConfirmationEntity();

        confirm.setToken(hashedUuid);
        confirm.setUserId(userId);
        confirm.setEmail(email);

        EmailConfirmationEntity updatedConfirm = ecRepository.save(confirm);

        TokenWrapper<String, EmailConfirmationEntity> tw = new TokenWrapper<String, EmailConfirmationEntity>(uuid, updatedConfirm);

        return new ServiceReturn<>(false, null, null, tw);
    }

}
