package com.myplaygroup.server.schedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private DailyClassType classtype;

    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled = false;

    public DailyClass(LocalDate date, LocalTime startTime, LocalTime endTime, DailyClassType classtype) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classtype = classtype;
    }
}