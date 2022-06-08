package com.myplaygroup.server.schedule.model;

import com.myplaygroup.server.user.model.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "monthly_plan",
        uniqueConstraints = {
                @UniqueConstraint(name = "kid_name_unique", columnNames = "kid_name"),
        }
)
public class MonthlyPlan {
    @Id
    @SequenceGenerator(
            name = "monthly_plan_sequence",
            sequenceName = "monthly_plan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "monthly_plan_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "kid_name", nullable = false)
    private String kidName;

    @ManyToOne
    @JoinColumn(name = "app_user", nullable = false)
    private AppUser appUser;

    @Column(name = "month", nullable = false)
    private Month month;

    @ManyToOne
    @JoinColumn(name = "plan", nullable = false)
    private StandardPlan plan;

    @ManyToMany
    @Column(name = "classes", nullable = false)
    private List<DailyClass> classes;

    @Column(name = "paid", nullable = false)
    private Boolean paid = false;

    @ElementCollection(targetClass = DayOfWeek.class)
    @JoinTable(name = "days_of_week", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "days_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> daysOfWeek;

    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled = false;

    public MonthlyPlan(String kidName, AppUser appUser, Month month, StandardPlan plan, List<DailyClass> classes, List<DayOfWeek> daysOfWeek) {
        this.kidName = kidName;
        this.appUser = appUser;
        this.month = month;
        this.plan = plan;
        this.classes = classes;
        this.daysOfWeek = daysOfWeek;
    }
}
