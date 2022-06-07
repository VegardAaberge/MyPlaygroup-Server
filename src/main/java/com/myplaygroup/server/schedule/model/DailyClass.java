package com.myplaygroup.server.schedule.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myplaygroup.server.schedule.requests.DailyClassItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "daily_class")
public class DailyClass {
    @Id
    @SequenceGenerator(
            name = "daily_class_sequence",
            sequenceName = "daily_class_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "daily_class_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "class_type", nullable = false)
    private DailyClassType classType;

    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled = false;

    public DailyClass(LocalDate date, LocalTime startTime, LocalTime endTime, DailyClassType classType, DayOfWeek dayOfWeek) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classType = classType;
        this.dayOfWeek = dayOfWeek;
    }

    public DailyClass(DailyClassItem item){
        this.id = item.id;
        this.date = item.date;
        this.startTime = item.startTime;
        this.endTime = item.endTime;
        this.classType = item.classType;
        this.dayOfWeek = item.dayOfWeek;
        this.cancelled = item.cancelled;
    }
}
