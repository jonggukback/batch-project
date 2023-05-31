package com.batch.pass.repository.packaze;

import com.batch.pass.entity.packaze.Package;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface PackageRepository extends JpaRepository<Package, Integer> {
    List<Package> findByCreatedAtAfter(LocalDateTime dateTime, Pageable packageSeq);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Package p SET p.count = :count, p.period = :period WHERE p.packageSeq = :packageSeq")
    int updateCountAndPeriod(
            @Param("packageSeq") Integer packageSeq,
            @Param("count") Integer count,
            @Param("period") Integer period);

}
