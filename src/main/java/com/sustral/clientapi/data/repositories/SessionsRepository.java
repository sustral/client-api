package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.SessionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionsRepository extends JpaRepository<SessionsEntity, String> {
}
