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
                    "   date =?1 " +
                    "   AND class_type=?2"
    )
    Optional<DailyClass> findByDateAndClassType(LocalDate date, Integer classType);

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM daily_class " +
                    "WHERE " +
                    "   day_of_week IN ?1 " +
                    "   AND class_type=?2 " +
                    "   AND date BETWEEN ?3 AND ?4 "
    )
    List<DailyClass> findByDatesAndClassType(List<Integer> date, Integer classType, LocalDate startDate, LocalDate endDate);
}
