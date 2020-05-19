package com.sustral.clientapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.*;

/**
 * This class overrides the production specific config so that it is not used during testing.
 *
 * @author Dilanka Dharmasena
 */
@Configuration
@Profile("test")
public class TestConfig {

    /**
     * Sets the default logger during testing.
     */
    @Bean
    @Primary
    @Scope("prototype")
    public Logger getTestingLogger(InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }

}
