package com.myplaygroup.server.schedule.repository;

import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.response.MonthlyPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MonthlyPlanRepository extends JpaRepository<MonthlyPlan, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM monthly_plan " +
                    "    JOIN app_user " +
                    "        ON app_user.id = monthly_plan.app_user " +
                    "WHERE " +
                    "   app_user.username= ?1" +
                    "   AND monthly_plan.cancelled = false"
    )
    List<MonthlyPlan> findByUsername(String username);
}
