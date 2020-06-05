package com.sustral.clientapi.temporaryrobotapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * This class inserts robot api specific filters into the filter chain.
 *
 * @author Dilanka Dharmasena
 */
@ConditionalOnProperty(name = "robotApi.enabled", havingValue = "true")
@Configuration
public class MissionFilterConfiguration {

    private final String robotApiToken;

    public MissionFilterConfiguration(@Value("${robotApi.token}") String robotApiToken) {
        this.robotApiToken = robotApiToken;
    }

    @Bean
    public FilterRegistrationBean<MissionFilter> missionFilterRegistration() {
        FilterRegistrationBean<MissionFilter> registrationBean = new FilterRegistrationBean<>();
        MissionFilter filter = new MissionFilter(robotApiToken);
        registrationBean.setFilter(filter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE+1); // Set as second in chain
        registrationBean.addUrlPatterns("/missions/*");
        return registrationBean;
    }

}
