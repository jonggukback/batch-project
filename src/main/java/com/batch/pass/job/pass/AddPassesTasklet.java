package com.batch.pass.job.pass;
import com.batch.pass.entity.pass.BulkPass;
import com.batch.pass.entity.pass.BulkPassStatus;
import com.batch.pass.entity.pass.Pass;
import com.batch.pass.entity.user.UserGroupMapping;
import com.batch.pass.repository.pass.BulkPassRepository;
import com.batch.pass.repository.pass.PassModelMapper;
import com.batch.pass.repository.pass.PassRepository;
import com.batch.pass.repository.user.UserGroupMappingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AddPassesTasklet implements Tasklet {
    private final PassRepository passRepository;
    private final BulkPassRepository bulkPassRepository;
    private final UserGroupMappingRepository userGroupMappingRepository;

    public AddPassesTasklet(PassRepository passRepository, BulkPassRepository bulkPassRepository, UserGroupMappingRepository userGroupMappingRepository) {
        this.passRepository = passRepository;
        this.bulkPassRepository = bulkPassRepository;
        this.userGroupMappingRepository = userGroupMappingRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        // 이용권 시작 일시 1일 전 user group 내 각 사용자에게 이용권을 추가해줍니다.
        final LocalDateTime startedAt = LocalDateTime.now().minusDays(1);
        final List<BulkPass> bulkPassEntities = bulkPassRepository.findByStatusAndStartedAtGreaterThan(BulkPassStatus.READY, startedAt);

        int count = 0;
        for (BulkPass bulkPassEntity : bulkPassEntities) {
            // user group에 속한 userId들을 조회합니다.
            final List<String> userIds = userGroupMappingRepository.findByUserGroupId(bulkPassEntity.getUserGroupId())
                    .stream().map(UserGroupMapping::getUserId).toList();

            // 각 userId로 이용권을 추가합니다.
            count += addPasses(bulkPassEntity, userIds);
            // pass 추가 이후 상태를 COMPLETED로 업데이트합니다.
            bulkPassEntity.setStatus(BulkPassStatus.COMPLETED);

        }
        log.info("AddPassesTasklet - execute: 이용권 {}건 추가 완료, startedAt={}", count, startedAt);
        return RepeatStatus.FINISHED;

    }

    // bulkPass의 정보로 pass 데이터를 생성합니다.
    private int addPasses(BulkPass bulkPassEntity, List<String> userIds) {
        List<Pass> passEntities = new ArrayList<>();
        for (String userId : userIds) {
            Pass passEntity = PassModelMapper.INSTANCE.toPassEntity(bulkPassEntity, userId);
            passEntities.add(passEntity);

        }
        return passRepository.saveAll(passEntities).size();

    }

}