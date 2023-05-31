package com.batch.pass.repository;

import com.batch.pass.entity.pass.BulkPass;
import com.batch.pass.entity.pass.BulkPassStatus;
import com.batch.pass.entity.pass.Pass;
import com.batch.pass.entity.pass.PassStatus;
import com.batch.pass.repository.pass.PassModelMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
class PassModelMapperTest {
    @Test
    public void test_toPassEntity() {
        // given
        final LocalDateTime now = LocalDateTime.now();
        final String userId = "A1000000";

        BulkPass bulkPassEntity = new BulkPass();
        bulkPassEntity.setPackageSeq(1);
        bulkPassEntity.setUserGroupId("GROUP");
        bulkPassEntity.setStatus(BulkPassStatus.COMPLETED);
        bulkPassEntity.setCount(10);
        bulkPassEntity.setStartedAt(now.minusDays(60));
        bulkPassEntity.setEndedAt(now);

        // when
        final Pass passEntity = PassModelMapper.INSTANCE.toPassEntity(bulkPassEntity, userId);

        // then
        assertEquals(1, passEntity.getPackageSeq());
        assertEquals(PassStatus.READY, passEntity.getStatus());
        assertEquals(10, passEntity.getRemainingCount());
        assertEquals(now.minusDays(60), passEntity.getStartedAt());
        assertEquals(now, passEntity.getEndedAt());

    }
}