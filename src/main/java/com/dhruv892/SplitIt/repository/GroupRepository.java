package com.dhruv892.SplitIt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dhruv892.SplitIt.entities.GroupEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

}
