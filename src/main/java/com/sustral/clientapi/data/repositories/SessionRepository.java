package com.sustral.clientapi.data.repositories;

import com.sustral.clientapi.data.models.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionEntity, String> {
}
