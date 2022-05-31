package com.myplaygroup.server.schedule.repository;

import com.myplaygroup.server.schedule.model.StandardPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface StandardPlanRepository extends JpaRepository<StandardPlan, Long> {

    Optional<StandardPlan> findByName(String standardPlan);
}
