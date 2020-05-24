package com.sustral.clientapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class instantiates the default logger that is to be used in the rest of the app.
 *
 * @author Dilanka Dharmasena
 */
@Configuration
public class LoggerConfig {

    /**
     * Sets the default logger to the sustral logback logger defined in logback-spring.xml.
     */
    @Bean
    public static Logger getLogger() {
        return LoggerFactory.getLogger("com.example.sustral.logback");
    }

}
