package com.myplaygroup.server.chat.model;

import com.myplaygroup.server.user.model.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.myplaygroup.server.shared.utils.Constants.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "message",
        uniqueConstraints = {
                @UniqueConstraint(name = "client_id_unique", columnNames = "client_id"),
        }
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

    @NotBlank(message = CLIENT_ID_VALIDATION_MSG)
    @Column(name = "client_id", updatable = false, nullable = false)
    private String clientId;

    @NotBlank(message = MESSAGE_VALIDATION_MSG)
    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser createdBy;

    @ManyToMany
    @NotNull
    @Column(name = "readBy", nullable = false)
    private List<AppUser> readBy;

    @ManyToMany
    @Size(min = 1, message = RECEIVERS_VALIDATION_MSG)
    @Column(name = "receivers", nullable = false)
    private List<AppUser> receivers;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    public Message(String clientId, String message, AppUser createdBy, List<AppUser> receivers, List<AppUser> readBy) {
        this.clientId = clientId;
        this.message = message;
        this.createdBy = createdBy;
        this.receivers = receivers;
        this.readBy = readBy;
        this.created = LocalDateTime.now();
    }
}
