package com.sustral.clientapi.filters;

import com.sustral.clientapi.miscservices.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

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
    private final String allowedDomain;

    @Autowired
    public FilterConfiguration(JWTService jwtService, @Value("${deployment.allowedDomain}") String allowedDomain) {
        this.jwtService = jwtService;
        this.allowedDomain = allowedDomain;
    }

    private int order(int position) {
        // Lower integers signify higher precedence when ordering filters. Higher precedence means earlier in the chain
        return Ordered.HIGHEST_PRECEDENCE + position;
    }

    @Bean
    public FilterRegistrationBean<GatewayFilter> gatewayFilterRegistration() {
        FilterRegistrationBean<GatewayFilter> registrationBean = new FilterRegistrationBean<>();
        GatewayFilter filter = new GatewayFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(order(0));
        registrationBean.addUrlPatterns("*"); // Used for all endpoints
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SessionCSRFFilter> sessionCSRFFilterRegistration() {
        FilterRegistrationBean<SessionCSRFFilter> registrationBean = new FilterRegistrationBean<>();
        SessionCSRFFilter filter = new SessionCSRFFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(order(2));
        registrationBean.addUrlPatterns("/sign_out/*");
        registrationBean.addUrlPatterns("/session/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<UserAuthenticationFilter> userAuthenticationFilterRegistration() {
        FilterRegistrationBean<UserAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        UserAuthenticationFilter filter = new UserAuthenticationFilter(jwtService);
        registrationBean.setFilter(filter);
        registrationBean.setOrder(order(2)); // Set as third in chain
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

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(allowedDomain));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("Sustral-Client-Type", "Sustral-CSRF", "Content-Type", "Content-Length"));
        config.setAllowedMethods(List.of("POST", "OPTIONS"));
        config.setMaxAge(1800L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        CorsFilter filter = new CorsFilter(source);
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setOrder(order(1));
        return registrationBean;
    }

}
