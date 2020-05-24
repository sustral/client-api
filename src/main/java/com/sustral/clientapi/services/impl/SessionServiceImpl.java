package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.SessionEntity;
import com.sustral.clientapi.data.repositories.SessionRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.services.SessionService;
import com.sustral.clientapi.services.types.TokenWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * An implementation of SessionService.
 *
 * @author Dilanka Dharmasena
 */
@Service
public class SessionServiceImpl implements SessionService {

    private static final long FOUR_DAYS = 4 * 24 * 60 * 60 * 1000; // Four days in ms form to check age of session token

    private final SessionRepository sessionRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, IdGenerator idGenerator) {
        this.sessionRepository = sessionRepository;
        this.idGenerator = idGenerator;
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
        long cutoff = System.currentTimeMillis() - FOUR_DAYS;

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
