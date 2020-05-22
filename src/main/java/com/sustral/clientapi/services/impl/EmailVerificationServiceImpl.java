package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.EmailVerificationEntity;
import com.sustral.clientapi.data.repositories.EmailVerificationRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.services.EmailVerificationService;
import com.sustral.clientapi.services.types.ServiceReturn;
import com.sustral.clientapi.services.types.TokenWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * An implementation of EmailVerificationService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private static final long ONE_DAY = 24 * 60 * 60 * 1000; // One day in ms to be used as a cutoff

    @Autowired
    private EmailVerificationRepository evRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public ServiceReturn<EmailVerificationEntity> getOneAndDeleteByToken(String token) {
        String hashedToken = DigestUtils.sha256Hex(token);

        Optional<EmailVerificationEntity> verify = evRepository.findById(hashedToken);

        if (verify.isEmpty()) {
            return new ServiceReturn<>("E0000", null);
        }

        // Check if the token is expired
        long created = verify.get().getCreated().getTime();
        long cutoff = System.currentTimeMillis() - ONE_DAY;

        if (created < cutoff) {
            evRepository.delete(verify.get());
            return new ServiceReturn<>("E0000", null);
        }

        // Delete the used token

        evRepository.delete(verify.get());

        return new ServiceReturn<>(null, verify.get());
    }

    @Override
    public ServiceReturn<TokenWrapper<String, EmailVerificationEntity>> create(String userId, String email) {

        String newId = idGenerator.generateId(); // Sent to the user as the token
        String hashedId = DigestUtils.sha256Hex(newId);

        EmailVerificationEntity verify = new EmailVerificationEntity();

        verify.setToken(hashedId);
        verify.setUserId(userId);
        verify.setEmail(email);

        EmailVerificationEntity updatedVerify = evRepository.save(verify);

        TokenWrapper<String, EmailVerificationEntity> tw = new TokenWrapper<>(newId, updatedVerify);

        return new ServiceReturn<>(null, tw);
    }

}
