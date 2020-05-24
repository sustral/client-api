package com.sustral.clientapi.dataservices.impl;

import com.sustral.clientapi.data.models.SessionEntity;
import com.sustral.clientapi.data.repositories.SessionRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.dataservices.SessionService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
import com.sustral.clientapi.utils.ConfigurationParser;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * An implementation of SessionService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class SessionServiceImpl implements SessionService {

    private final long sessionExpiry;

    private final SessionRepository sessionRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, IdGenerator idGenerator,
                              @Value("${sustral.security.sessionExpiration}") String sessionExpiryConfig) {
        this.sessionRepository = sessionRepository;
        this.idGenerator = idGenerator;
        this.sessionExpiry = ConfigurationParser.parseTime(sessionExpiryConfig);
    }

    @Override
    public SessionEntity findOneAndDeleteByToken(String token) {
        String hashedToken = DigestUtils.sha256Hex(token);

        Optional<SessionEntity> session = sessionRepository.findById(hashedToken);

        if (session.isEmpty()) {
            return null;
        }

        // Delete used token
        sessionRepository.delete(session.get());

        // Check if the token is expired
        long created = session.get().getCreated().getTime();
        long cutoff = System.currentTimeMillis() - sessionExpiry;

        if (created < cutoff) {
            return null;
        }

        return session.get();
    }

    @Override
    public TokenWrapper<String, SessionEntity> create(String userId) {

        String newId = idGenerator.generateId(); // This is sent to the user as a token
        String hashedId = DigestUtils.sha256Hex(newId);

        SessionEntity session = new SessionEntity();

        session.setToken(hashedId);
        session.setUserId(userId);

        SessionEntity updatedSession = sessionRepository.save(session); // Guaranteed to not be null

        return new TokenWrapper<>(newId, updatedSession);
    }

}
