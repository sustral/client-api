package com.sustral.clientapi.services.impl;

import com.sustral.clientapi.data.models.SessionEntity;
import com.sustral.clientapi.data.repositories.SessionRepository;
import com.sustral.clientapi.data.utils.idgenerator.IdGenerator;
import com.sustral.clientapi.services.SessionService;
import com.sustral.clientapi.services.types.ServiceReturn;
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

    private static final int FOUR_DAYS = 4 * 24 * 60 * 60 * 1000; // Four days in ms form to check age of session token

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private IdGenerator idGenerator;


    @Override
    public ServiceReturn<SessionEntity> findOneAndDeleteByToken(String token) {
        String hashedToken = DigestUtils.sha256Hex(token);

        Optional<SessionEntity> session = sessionRepository.findById(hashedToken);

        if (session.isEmpty()) {
            return new ServiceReturn<>("E0000", null);
        }

        // Check if the token is expired
        long created = session.get().getCreated().getTime();
        long cutoff = System.currentTimeMillis() - FOUR_DAYS;

        if (created < cutoff) {
            sessionRepository.delete(session.get());
            return new ServiceReturn<>("E0000", null);
        }

        // Delete used token

        sessionRepository.delete(session.get());

        return new ServiceReturn<>(null, session.get());
    }

    @Override
    public ServiceReturn<TokenWrapper<String, SessionEntity>> create(String userId) {

        String newId = idGenerator.generateId(); // This is sent to the user as a token
        String hashedId = DigestUtils.sha256Hex(newId);

        SessionEntity session = new SessionEntity();

        session.setToken(hashedId);
        session.setUserId(userId);

        SessionEntity updatedSession = sessionRepository.save(session);

        TokenWrapper<String, SessionEntity> tw = new TokenWrapper<>(newId, updatedSession);

        return new ServiceReturn<>(null, tw);
    }

}
