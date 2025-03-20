package com.dhruv892.SplitIt.repository;

import com.dhruv892.SplitIt.entities.GroupBalanceEntity;
import com.dhruv892.SplitIt.entities.GroupEntity;
import com.dhruv892.SplitIt.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupBalanceRepository extends JpaRepository<GroupBalanceEntity, Long> {
    Optional<GroupBalanceEntity> findByUserAndGroup(UserEntity user, GroupEntity group);
    List<GroupBalanceEntity> findByUser(UserEntity user);
}
