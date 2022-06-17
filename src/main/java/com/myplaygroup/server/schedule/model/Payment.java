package com.myplaygroup.server.schedule.model;

import com.myplaygroup.server.user.model.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.myplaygroup.server.shared.utils.Constants.CLIENT_ID_VALIDATION_MSG;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "payment",
        uniqueConstraints = {
                @UniqueConstraint(name = "client_id_unique", columnNames = "client_id"),
        }
)
public class Payment {
    @Id
    @SequenceGenerator(
            name = "payment_sequence",
            sequenceName = "payment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull(message = CLIENT_ID_VALIDATION_MSG)
    @Column(name = "client_id", updatable = false)
    public String clientId = UUID.randomUUID().toString();

    @Column(name = "start_date", nullable = false)
    private LocalDate date;

    @NotNull
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "app_user", nullable = false)
    private AppUser appUser;

    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled = false;

    public Payment(String clientId, LocalDate date, Long amount, AppUser appUser) {
        this.clientId = clientId;
        this.date = date;
        this.amount = amount;
        this.appUser = appUser;
    }
}
