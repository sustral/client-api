package com.sustral.clientapi.data.repositories;

import static org.assertj.core.api.Assertions.*;

import com.sustral.clientapi.data.models.UserOrganizationRelationshipEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserOrganizationRepositoryIntegrationTests {

    @Autowired
    UserOrganizationRelationshipRepository uorRepository;

    @Test
    void findAllByUserIdTest() {

        List<UserOrganizationRelationshipEntity> uorUA = uorRepository.findAllByUserId("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThat(uorUA.size()).isEqualTo(2);

        List<UserOrganizationRelationshipEntity> uorNone = uorRepository.findAllByUserId("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThat(uorNone.size()).isEqualTo(0);

    }

    @Test
    void findAllByOrganizationIdTest() {

        List<UserOrganizationRelationshipEntity> uorOE = uorRepository.findAllByOrganizationId("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        assertThat(uorOE.size()).isEqualTo(2);

        List<UserOrganizationRelationshipEntity> uorNone = uorRepository.findAllByOrganizationId("Aeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        assertThat(uorNone.size()).isEqualTo(0);

    }

}
