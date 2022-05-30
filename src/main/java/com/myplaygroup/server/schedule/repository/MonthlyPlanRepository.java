package com.myplaygroup.server.schedule.repository;

import com.myplaygroup.server.schedule.model.MonthlyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MonthlyPlanRepository extends JpaRepository<MonthlyPlan, Long> {

    //List<MonthlyPlan> findByUsername(String username);
}