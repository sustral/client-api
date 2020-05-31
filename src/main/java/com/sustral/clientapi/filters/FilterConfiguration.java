package com.sustral.clientapi.filters;

import com.sustral.clientapi.miscservices.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * This configuration class registers all the filters.
 *
 * @author Dilanka Dharmasena
 */
@Configuration
public class FilterConfiguration {

    // TODO: Put the Header and Cookie names in config.
    // TODO: Put filter to routes map in config

    private final JWTService jwtService;

    @Autowired
    public FilterConfiguration(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    // Lower integers signify higher precedence when ordering filters. Higher precedence means earlier in the chain

    @Bean
    public FilterRegistrationBean<GatewayFilter> gatewayFilterRegistration() {
        FilterRegistrationBean<GatewayFilter> registrationBean = new FilterRegistrationBean<>();
        GatewayFilter filter = new GatewayFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Set as first in chain
        registrationBean.addUrlPatterns("*"); // Used for all endpoints
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SessionCSRFFilter> sessionCSRFFilterRegistration() {
        FilterRegistrationBean<SessionCSRFFilter> registrationBean = new FilterRegistrationBean<>();
        SessionCSRFFilter filter = new SessionCSRFFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE+1); // Set as second in chain
        registrationBean.addUrlPatterns("/sign_out/*");
        registrationBean.addUrlPatterns("/session/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<UserAuthenticationFilter> userAuthenticationFilterRegistration() {
        FilterRegistrationBean<UserAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        UserAuthenticationFilter filter = new UserAuthenticationFilter(jwtService);
        registrationBean.setFilter(filter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE+1); // Set as second in chain
        registrationBean.addUrlPatterns("/verify_email/request/*");
        registrationBean.addUrlPatterns("/me/*");
        registrationBean.addUrlPatterns("/users/*");
        registrationBean.addUrlPatterns("/organizations/*");
        registrationBean.addUrlPatterns("/fields/*");
        registrationBean.addUrlPatterns("/scans/*");
        registrationBean.addUrlPatterns("/files/*");
        registrationBean.addUrlPatterns("/objects/*");
        registrationBean.addUrlPatterns("/users_to_organizations/*");
        registrationBean.addUrlPatterns("/fields_to_organizations/*");
        registrationBean.addUrlPatterns("/scans_to_fields/*");
        registrationBean.addUrlPatterns("/files_to_scans/*");
        return registrationBean;
    }

}
