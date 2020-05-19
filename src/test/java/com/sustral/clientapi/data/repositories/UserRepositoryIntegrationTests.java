package com.sustral.clientapi.data.repositories;

import static org.assertj.core.api.Assertions.*;

import com.sustral.clientapi.data.models.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserRepositoryIntegrationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByEmailTest() {

        Optional<UserEntity> userA = userRepository.findByEmail("user_a@example.com");
        assertThat(userA.isPresent()).isTrue();
        assertThat(userA.get().getEmail()).isEqualTo("user_a@example.com");

        Optional<UserEntity> userNone = userRepository.findByEmail("nonexistent@example.com");
        assertThat(userNone.isPresent()).isFalse();

    }

    @Test
    void existsByEmailTest() {

        boolean userAExists = userRepository.existsByEmail("user_a@example.com");
        assertThat(userAExists).isTrue();

        boolean userNoneExists = userRepository.existsByEmail("nonexistent@example.com");
        assertThat(userNoneExists).isFalse();

    }

}
