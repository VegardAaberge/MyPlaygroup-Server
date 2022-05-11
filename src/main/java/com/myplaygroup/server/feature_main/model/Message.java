package com.myplaygroup.server.feature_main.model;

import com.myplaygroup.server.feature_login.model.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser createdBy;

    @ManyToMany
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
