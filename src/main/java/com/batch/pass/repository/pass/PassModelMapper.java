package com.batch.pass.repository.pass;

import com.batch.pass.entity.pass.BulkPass;
import com.batch.pass.entity.pass.BulkPassStatus;
import com.batch.pass.entity.pass.Pass;
import com.batch.pass.entity.pass.PassStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

// ReportingPolicy.IGNORE: 일치하지 않은 필드를 무시합니다.
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PassModelMapper {
    PassModelMapper INSTANCE = Mappers.getMapper(PassModelMapper.class);

    // 필드명이 같지 않거나 custom하게 매핑해주기 위해서는 @Mapping을 추가해주면 됩니다.
    @Mapping(target = "status", qualifiedByName = "defaultStatus")
    @Mapping(target = "remainingCount", source = "bulkPassEntity.count")
    Pass toPassEntity(BulkPass bulkPassEntity, String userId);

    // BulkPassStatus와 관계 없이 PassStatus값을 설정합니다.
    @Named("defaultStatus")
    default PassStatus status(BulkPassStatus status) {
        return PassStatus.READY;
    }

}
