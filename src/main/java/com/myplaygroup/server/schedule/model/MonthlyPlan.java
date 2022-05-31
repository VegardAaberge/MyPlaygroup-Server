package com.myplaygroup.server.schedule.model;

import com.myplaygroup.server.user.model.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "plan", nullable = false)
    private StandardPlan plan;

    @ManyToMany
    @Column(name = "classes", nullable = false)
    private List<DailyClass> classes;

    @Column(name = "paid", nullable = false)
    private Boolean paid = false;

    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled = false;

    public MonthlyPlan(String kidName, AppUser appUser, StandardPlan plan, List<DailyClass> classes) {
        this.kidName = kidName;
        this.appUser = appUser;
        this.plan = plan;
        this.classes = classes;
    }
}
