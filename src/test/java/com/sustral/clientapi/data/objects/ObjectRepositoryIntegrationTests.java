package com.sustral.clientapi.data.objects;

import static org.assertj.core.api.Assertions.*;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class ObjectRepositoryIntegrationTests {

    @Autowired
    ObjectRepository objectRepository;

    @Test
    void downloadTest() throws IOException {

        InputStream is1 = objectRepository.download("downloadTest.txt");
        assertThat(is1).isNotNull();
        byte[] is1Bytes = IOUtils.toByteArray(is1);
        assertThat(is1Bytes.length).isEqualTo(31); // Size of the file
        is1.close();

    }

}
