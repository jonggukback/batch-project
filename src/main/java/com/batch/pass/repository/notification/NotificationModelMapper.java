package com.batch.pass.repository.notification;

import com.batch.pass.entity.booking.Booking;
import com.batch.pass.entity.notification.Notification;
import com.batch.pass.entity.notification.NotificationEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationModelMapper {

    NotificationModelMapper INSTANCE = Mappers.getMapper(NotificationModelMapper.class);

    @Mapping(target = "uuid", source = "booking.user.uuid")
    @Mapping(target = "text", source = "booking.startedAt", qualifiedByName = "text")
    Notification toNotification(Booking booking, NotificationEvent event);

    @Named("text")
    default String text(LocalDateTime startedAt) {
        return String.format("안녕하세요. %s 수업 곧 시작합니다. 수업 전 출석 체크 부탁드립니다. \uD83D\uD83D", startedAt);
    }
}
