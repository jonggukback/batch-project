package com.batch.pass.repository;

import com.batch.pass.entity.pass.BulkPass;
import com.batch.pass.entity.pass.BulkPassStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BulkPassRepository extends JpaRepository<BulkPass, Integer> {

    // WHERE status = :status AND startedAt > :startedAt
    List<BulkPass> findByStatusAndStartedAtGreaterThan(BulkPassStatus status, LocalDateTime startedAt);
}
