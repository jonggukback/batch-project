package com.batch.pass.repository.booking;

import com.batch.pass.entity.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Booking b SET b.usedPass = :usedPass, b.modifiedAt = CURRENT_TIMESTAMP WHERE b.passSeq = :passSeq")
    int updateUsedPass(Integer passSeq, boolean usedPass);
}
