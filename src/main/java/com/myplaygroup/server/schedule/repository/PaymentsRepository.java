package com.myplaygroup.server.schedule.repository;

import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PaymentsRepository extends JpaRepository<Payment, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM payment " +
                    "    JOIN app_user " +
                    "        ON app_user.id = payment.app_user " +
                    "WHERE " +
                    "   app_user.username= ?1" +
                    "   AND payment.cancelled = false"
    )
    List<Payment> findByUsername(String username);
}
