package com.batch.pass.entity.user;

import com.batch.pass.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
// json의 타입을 정의합니다.
@TypeDef(name = "json", typeClass = JsonType.class)
public class User extends BaseEntity {
    @Id
    private String userId;

    private String userName;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String phone;

    @Type(type = "json")
    private Map<String, Object> meta;

    public String getUuid(){
        String uuid = null;
        if (meta.containsKey("uuid")){
            uuid = String.valueOf(meta.get("uuid"));
        }

        return uuid;
    }
}