package com.myplaygroup.server.schedule.repository;

import com.myplaygroup.server.schedule.model.StandardPlan;
import com.myplaygroup.server.schedule.response.StandardPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface StandardPlanRepository extends JpaRepository<StandardPlan, Long> {

    Optional<StandardPlan> findByName(String standardPlan);

    @Query("SELECT s FROM StandardPlan s")
    List<StandardPlanItem> getAllStandardPlans();
}
