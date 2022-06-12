package com.myplaygroup.server.schedule.model;

import com.myplaygroup.server.user.model.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import static com.myplaygroup.server.shared.utils.Constants.CLIENT_ID_VALIDATION_MSG;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "monthly_plan",
        uniqueConstraints = {
                @UniqueConstraint(name = "client_id_unique", columnNames = "client_id"),
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

    @NotNull(message = CLIENT_ID_VALIDATION_MSG)
    @Column(name = "client_id", updatable = false)
    public String clientId = UUID.randomUUID().toString();

    @Column(name = "kid_name", nullable = false)
    private String kidName;

    @ManyToOne
    @JoinColumn(name = "app_user", nullable = false)
    private AppUser appUser;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "plan", nullable = false)
    private StandardPlan plan;

    @NotNull
    private Long planPrice;

    @ManyToMany
    @Column(name = "classes", nullable = false)
    private List<DailyClass> classes;

    @ElementCollection(targetClass = DayOfWeek.class)
    @JoinTable(name = "days_of_week", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "days_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> daysOfWeek;

    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled = false;

    public MonthlyPlan(String clientId,
                       String kidName,
                       AppUser appUser,
                       LocalDate startDate,
                       LocalDate endDate,
                       StandardPlan plan,
                       Long planPrice,
                       List<DailyClass> classes,
                       List<DayOfWeek> daysOfWeek) {
        this.clientId = clientId;
        this.kidName = kidName;
        this.appUser = appUser;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plan = plan;
        this.planPrice = planPrice;
        this.classes = classes;
        this.daysOfWeek = daysOfWeek;
    }
}
