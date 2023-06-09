package com.batch.pass.repository.pass;

import com.batch.pass.entity.pass.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PassRepository extends JpaRepository<Pass, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Pass p" +
            "          SET p.remainingCount = :remainingCount," +
            "              p.modifiedAt = CURRENT_TIMESTAMP" +
            "        WHERE p.passSeq = :passSeq")
    int updateRemainingCount(Integer passSeq, Integer remainingCount);
}
