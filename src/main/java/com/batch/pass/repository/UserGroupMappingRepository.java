package com.batch.pass.repository;

import com.batch.pass.entity.user.UserGroupMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupMappingRepository extends JpaRepository<UserGroupMapping, Integer> {
    List<UserGroupMapping> findByUserGroupId(String userGroupId);
}
