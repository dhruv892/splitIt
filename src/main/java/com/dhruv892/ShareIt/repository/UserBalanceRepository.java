package com.dhruv892.ShareIt.repository;

import com.dhruv892.ShareIt.entities.UserBalanceEntity;
import com.dhruv892.ShareIt.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalanceEntity, Long> {
    Optional<UserBalanceEntity> findByUser1AndUser2(UserEntity user1, UserEntity user2);
    List<UserBalanceEntity> findByUser1OrUser2(UserEntity user1, UserEntity user2);
}
