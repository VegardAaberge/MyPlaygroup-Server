package com.myplaygroup.server.schedule.repository;

import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.response.MonthlyPlanResponse;
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
            value = "SELECT " +
                    "    monthly_plan.id as id, " +
                    "    monthly_plan.kid_name as kidName, " +
                    "    monthly_plan.paid as paid, " +
                    "    standard_plan.name as planName, " +
                    "    standard_plan.price as planPrice " +
                    "FROM monthly_plan " +
                    "    JOIN app_user " +
                    "        ON app_user.id = monthly_plan.app_user " +
                    "    JOIN standard_plan " +
                    "        ON standard_plan.id = monthly_plan.plan " +
                    "WHERE " +
                    "   app_user.username= ?1" +
                    "   AND monthly_plan.cancelled = false"
    )
    List<MonthlyPlanResponse> findByUsername(String username);
}
