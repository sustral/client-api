package com.sustral.clientapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: Null check every parameter.
// TODO: Add in the ELKB logger and replace the placeholder System.err.
// TODO: Null check fields of passed in objects.

@SpringBootApplication
public class ClientapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientapiApplication.class, args);
    }

}
