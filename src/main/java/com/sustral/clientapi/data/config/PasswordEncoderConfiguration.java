package com.sustral.clientapi.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class instantiates the PasswordEncoder used in the rest of the application.
 *
 * @author Dilanka Dharmasena
 */
@Configuration
public class PasswordEncoderConfiguration {

    /**
     * Sets the default passwordEncoder to a BCrypt encoder.
     *
     * @return  a PasswordEncoder
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
