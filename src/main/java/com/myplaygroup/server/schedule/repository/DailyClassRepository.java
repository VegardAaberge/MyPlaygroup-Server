package com.myplaygroup.server.schedule.repository;

import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.DailyClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DailyClassRepository extends JpaRepository<DailyClass, Long> {

    //Optional<DailyClass> findByDateAndClassType(LocalDate date, LocalTime startTime);

    //List<DailyClass> findByDaysAndClassType(List<LocalDate> days, DailyClassType type);
}
