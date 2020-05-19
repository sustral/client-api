package com.sustral.clientapi.data.repositories;

import static org.assertj.core.api.Assertions.*;

import com.sustral.clientapi.data.models.FileEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FileRepositoryIntegrationTests {

    @Autowired
    FileRepository fileRepository;

    @Test
    void findAllByFieldIdAndScanIdTest() {

        List<FileEntity> filesGS1 = fileRepository.findAllByFieldIdAndScanId("gggggggggggggggggggggggggggggggg", "s1gggggggggggggggggggggggggggggg");
        assertThat(filesGS1.size()).isEqualTo(3);

        List<FileEntity> filesGNone = fileRepository.findAllByFieldIdAndScanId("gggggggggggggggggggggggggggggggg", "NOgggggggggggggggggggggggggggggg");
        assertThat(filesGNone.size()).isEqualTo(0);

        List<FileEntity> filesNoneS1 = fileRepository.findAllByFieldIdAndScanId("Gggggggggggggggggggggggggggggggg", "s1gggggggggggggggggggggggggggggg");
        assertThat(filesNoneS1.size()).isEqualTo(0);

    }

}
