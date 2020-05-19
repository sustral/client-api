package com.sustral.clientapi;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class tests if the context loads.
 *
 * @author Dilanka Dharmasena
 */
@SpringBootTest
class ClientapiApplicationTests {

    @Autowired
    Logger logger; // Just picked a random bean that should always be initialized

    @Test
    void contextLoads() {
        assertThat(logger).isNotNull();
    }

}
