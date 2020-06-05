package com.sustral.clientapi.temporaryrobotapi;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MissionFilter implements Filter {

    private final String robotApiToken;

    public MissionFilter(String robotApiToken) {
        this.robotApiToken = robotApiToken;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String token = req.getHeader("Authorization");

        if (token != null) {
            token = token.substring(7); // Remove prepended "Bearer "
            if (token.equals(robotApiToken)) {
                chain.doFilter(request, response);
                return;
            }
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

}
