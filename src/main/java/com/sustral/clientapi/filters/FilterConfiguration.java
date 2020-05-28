package com.sustral.clientapi.filters;

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

}
