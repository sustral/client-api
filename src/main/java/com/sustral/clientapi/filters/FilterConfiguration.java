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

    @Bean
    public FilterRegistrationBean<GatewayFilter> gatewayFilterRegistration() {
        FilterRegistrationBean<GatewayFilter> registrationBean = new FilterRegistrationBean<>();
        GatewayFilter gatewayFilter = new GatewayFilter();
        registrationBean.setFilter(gatewayFilter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Set as first in chain
        registrationBean.addUrlPatterns("*"); // Used for all endpoints
        return registrationBean;
    }

}
