package com.myplaygroup.server.feature_main.model;

import com.myplaygroup.server.feature_login.model.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static com.myplaygroup.server.util.Constants.MESSAGE_VALIDATION_MSG;
import static com.myplaygroup.server.util.Constants.RECEIVERS_VALIDATION_MSG;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "message"
)
public class Message {

    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = MESSAGE_VALIDATION_MSG)
    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser createdBy;

    @ManyToMany
    @Size(min = 1, message = RECEIVERS_VALIDATION_MSG)
    @Column(name = "receivers", nullable = false)
    private List<AppUser> receivers;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    public Message(String message,
                   AppUser createdBy,
                   List<AppUser> receivers,
                   LocalDateTime created) {
        this.message = message;
        this.createdBy = createdBy;
        this.receivers = receivers;
        this.created = created;
    }
}
