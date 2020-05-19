package com.sustral.clientapi.data.repositories;

import static org.assertj.core.api.Assertions.*;

import com.sustral.clientapi.data.models.FieldOrganizationRelationshipEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FieldOrganizationRelationshipRepositoryIntegrationTests {

    @Autowired
    FieldOrganizationRelationshipRepository forRepository;

    @Test
    void findAllByFieldIdTest() {

        List<FieldOrganizationRelationshipEntity> forFJ = forRepository.findAllByFieldId("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
        assertThat(forFJ.size()).isEqualTo(2);

        List<FieldOrganizationRelationshipEntity> forNone = forRepository.findAllByFieldId("Ajjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
        assertThat(forNone.size()).isEqualTo(0);

    }

    @Test
    void findAllByOrganizationIdTest() {

        List<FieldOrganizationRelationshipEntity> forOE = forRepository.findAllByOrganizationId("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        assertThat(forOE.size()).isEqualTo(3);

        List<FieldOrganizationRelationshipEntity> forNone = forRepository.findAllByOrganizationId("Aeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        assertThat(forNone.size()).isEqualTo(0);

    }

}
