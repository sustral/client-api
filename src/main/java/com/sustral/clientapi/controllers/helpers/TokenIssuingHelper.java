package com.sustral.clientapi.controllers.helpers;

import com.sustral.clientapi.controllers.types.tokensets.MobileTokens;
import com.sustral.clientapi.controllers.types.tokensets.WebTokens;
import com.sustral.clientapi.data.models.SessionEntity;
import com.sustral.clientapi.data.models.UserEntity;
import com.sustral.clientapi.dataservices.SessionService;
import com.sustral.clientapi.dataservices.types.TokenWrapper;
import com.sustral.clientapi.miscservices.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This helper class streamlines the issuing of auth tokens.
 *
 * @author Dilanka Dharmasena
 */
@Component
public class TokenIssuingHelper {
    /*
    There is a lot of duplicated code between issueMobileTokens and issueWebTokens,
    but creating a helper function to eliminate ~5 lines will just increase complexity
    and make it more difficult to refactor if the two token sets drift apart in the future.
    Create a helper function in the future if there is a clear benefit. (v1.0.0)
     */

    private final SessionService sessionService;
    private final JWTService jwtService;

    @Autowired
    public TokenIssuingHelper(SessionService sessionService, JWTService jwtService) {
        this.sessionService = sessionService;
        this.jwtService = jwtService;
    }

    /**
     * Issues the tokens required to authenticate a mobile client.
     *
     * Issues a JSON Web Token and session token used to refresh the JWT.
     *
     * @param user  a UserEntity that represents the user requesting credentials
     * @return      a MobileTokens object
     */
    public MobileTokens issueMobileTokens(UserEntity user) {
        Map<String,Object> claimsMap = new HashMap<>();
        claimsMap.put("user", user.getId());
        String jwt = jwtService.issueToken(claimsMap);

        TokenWrapper<String, SessionEntity> sessionWrapper = sessionService.create(user.getId());

        if (jwt == null || sessionWrapper == null || sessionWrapper.getToken() == null) {
            return null;
        }

        String encodedSession = Base64.getEncoder().encodeToString(sessionWrapper.getToken().getBytes());

        return new MobileTokens(jwt, encodedSession);
    }

    /**
     * Issues the tokens required to authenticate a web based client.
     *
     * Issues a CSRF token, a JSON Web Token with the CSRF token as a claim, and
     * a session token used to refresh the CSRFT and JWT.
     *
     * @param user  a UserEntity that represents the user requesting credentials
     * @return      a WebTokens object
     */
    public WebTokens issueWebTokens(UserEntity user) {

        String csrf = UUID.randomUUID().toString().replace("-", ""); // Any sufficiently random and compact string will work

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("user", user.getId());
        claimsMap.put("csrf", csrf);
        String jwt = jwtService.issueToken(claimsMap);

        TokenWrapper<String,SessionEntity> sessionWrapper = sessionService.create(user.getId());

        if (jwt == null || sessionWrapper == null || sessionWrapper.getToken() == null) {
            return null;
        }

        String encodedCsrf = Base64.getEncoder().encodeToString(csrf.getBytes());
        String encodedSession = Base64.getEncoder().encodeToString(sessionWrapper.getToken().getBytes());

        return new WebTokens(jwt, encodedSession, encodedCsrf);
    }

    /**
     * This method will return whether a passed in session token is valid and then delete that token.
     *
     * @param token a String; the unmodified token sent by the user
     * @return      a boolean; true if the token is valid, false otherwise
     */
    public boolean isValidSession(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        SessionEntity session = sessionService.findOneAndDeleteByToken(decodedToken);
        return (session != null);
    }

}
