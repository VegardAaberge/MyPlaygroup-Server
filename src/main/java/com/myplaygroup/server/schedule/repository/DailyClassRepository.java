package com.myplaygroup.server.schedule.repository;

import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.DailyClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DailyClassRepository extends JpaRepository<DailyClass, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM daily_class " +
                    "WHERE " +
                    "   date >='2022-05-22 00:00:00' " +
                    "   AND date <'2022-05-23 00:00:00' " +
                    "   AND class_type=1"
    )
    Optional<DailyClass> findByDateAndClassType(LocalDate date, LocalTime startTime);

    //List<DailyClass> findByDaysAndClassType(List<LocalDate> days, DailyClassType type);
}
