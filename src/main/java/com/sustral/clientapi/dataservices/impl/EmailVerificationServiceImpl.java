package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.EmailVerificationEntity;
import com.sustral.clientapi.data.repositories.EmailVerificationRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.dataservices.EmailVerificationService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
import com.sustral.clientapi.utils.ConfigurationParser;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * An implementation of EmailVerificationService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final long evExpiry;

    private final EmailVerificationRepository evRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public EmailVerificationServiceImpl(EmailVerificationRepository evRepository, IdGenerator idGenerator,
                                        @Value("${sustral.security.emailVerificationExpiration}") String evExpiryConfig) {
        this.evRepository = evRepository;
        this.idGenerator = idGenerator;
        this.evExpiry = ConfigurationParser.parseTime(evExpiryConfig);
    }

    @Override
    public EmailVerificationEntity getOneAndDeleteByToken(String token) {
        String hashedToken = DigestUtils.sha256Hex(token);

        Optional<EmailVerificationEntity> verify = evRepository.findById(hashedToken);

        if (verify.isEmpty()) {
            return null;
        }

        // Delete the used token
        evRepository.delete(verify.get());

        // Check if the token is expired
        long created = verify.get().getCreated().getTime();
        long cutoff = System.currentTimeMillis() - evExpiry;

        if (created < cutoff) {
            return null;
        }

        return verify.get();
    }

    @Override
    public TokenWrapper<String, EmailVerificationEntity> create(String userId, String email) {

        String newId = idGenerator.generateId(); // Sent to the user as the token
        String hashedId = DigestUtils.sha256Hex(newId);

        EmailVerificationEntity verify = new EmailVerificationEntity();

        verify.setToken(hashedId);
        verify.setUserId(userId);
        verify.setEmail(email);

        EmailVerificationEntity updatedVerify = evRepository.save(verify); // Guaranteed to not be null

        return new TokenWrapper<>(newId, updatedVerify);
    }

}
