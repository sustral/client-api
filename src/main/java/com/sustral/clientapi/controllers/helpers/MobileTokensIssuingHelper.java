package com.sustral.clientapi.controllers.helpers;

import com.sustral.clientapi.controllers.types.tokensets.MobileTokens;
import com.sustral.clientapi.data.models.SessionEntity;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.SessionService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
import com.sustral.clientapi.miscservices.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Issues the tokens needed for authenticating mobile clients.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class MobileTokensIssuingHelper {

    private final SessionService sessionService;
    private final JWTService jwtService;

    @Autowired
    public MobileTokensIssuingHelper(SessionService sessionService, JWTService jwtService) {
        this.sessionService = sessionService;
        this.jwtService = jwtService;
    }

    public MobileTokens issueTokens(UserEntity user) {
        Map<String,Object> claimsMap = new HashMap<>();
        claimsMap.put("user", user.getId());
        String jwt = jwtService.issueToken(claimsMap);

        TokenWrapper<String, SessionEntity> sessionWrapper = sessionService.create(user.getId());

        if (jwt == null || sessionWrapper == null || sessionWrapper.getToken() == null) {
            return null;
        }

        return new MobileTokens(jwt, sessionWrapper.getToken());
    }

}
