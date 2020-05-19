package com.sustral.clientapi.data.repositories;

import static org.assertj.core.api.Assertions.*;

import com.sustral.clientapi.data.models.ScanEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ScanRepositoryIntegrationTests {

    @Autowired
    ScanRepository scanRepository;

    @Test
    void findAllByFieldIdTest() {

        List<ScanEntity> scansG = scanRepository.findAllByFieldId("gggggggggggggggggggggggggggggggg");
        assertThat(scansG.size()).isEqualTo(3);

        List<ScanEntity> scansNone = scanRepository.findAllByFieldId("Gggggggggggggggggggggggggggggggg");
        assertThat(scansNone.size()).isEqualTo(0);

    }

}
