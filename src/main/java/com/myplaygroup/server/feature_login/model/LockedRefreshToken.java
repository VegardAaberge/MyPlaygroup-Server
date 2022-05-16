package com.myplaygroup.server.feature_login.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Entity
@Table(
        name = "locked_refresh_token",
        uniqueConstraints = {
                @UniqueConstraint(name = "token_unique", columnNames = "token"),
        }
)
public class LockedRefreshToken {
    @Id
    @SequenceGenerator(
            name = "locked_refresh_token_sequence",
            sequenceName = "locked_refresh_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "locked_refresh_token_sequence"
    )
    @Column(name= "id", updatable = false)
    private long id;

    @Column(name = "token", nullable = false)
    private String  token;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "locked_time", nullable = false)
    private LocalDateTime lockedTime;

    public LockedRefreshToken(String token, String username, LocalDateTime lockedTime) {
        this.token = token;
        this.username = username;
        this.lockedTime = lockedTime;
    }
}
