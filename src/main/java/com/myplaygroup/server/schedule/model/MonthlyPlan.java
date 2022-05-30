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
@Table(name = "monthly_plan")
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

    @ManyToOne
    @JoinColumn(name = "app_user", nullable = false)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "plan", nullable = false)
    private StandardPlan plan;

    @ManyToMany
    @Column(name = "classes", nullable = false)
    private List<DailyClass> classes;

    public MonthlyPlan(AppUser appUser, StandardPlan plan, List<DailyClass> classes) {
        this.appUser = appUser;
        this.plan = plan;
        this.classes = classes;
    }
}
