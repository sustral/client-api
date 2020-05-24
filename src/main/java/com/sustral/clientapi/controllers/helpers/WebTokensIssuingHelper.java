package com.sustral.clientapi.controllers.helpers;

import com.sustral.clientapi.controllers.types.tokensets.WebTokens;
import com.sustral.clientapi.data.models.SessionEntity;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.SessionService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
import com.sustral.clientapi.miscservices.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Issues the tokens needed for authenticating web clients.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class WebTokensIssuingHelper {

    private final SessionService sessionService;
    private final JWTService jwtService;

    @Autowired
    public WebTokensIssuingHelper(SessionService sessionService, JWTService jwtService) {
        this.sessionService = sessionService;
        this.jwtService = jwtService;
    }

    public WebTokens issueTokens(UserEntity user) {

        String csrf = UUID.randomUUID().toString().replace("-", ""); // Any sufficiently random and compact string will work

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("user", user.getId());
        claimsMap.put("csrf", csrf);
        String jwt = jwtService.issueToken(claimsMap);

        TokenWrapper<String,SessionEntity> sessionWrapper = sessionService.create(user.getId());

        if (jwt == null || sessionWrapper == null || sessionWrapper.getToken() == null) {
            return null;
        }

        return new WebTokens(jwt, sessionWrapper.getToken(), csrf);
    }

}
