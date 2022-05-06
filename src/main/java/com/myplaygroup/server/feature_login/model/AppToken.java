package com.myplaygroup.server.feature_login.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "app_token",
        uniqueConstraints = {
                @UniqueConstraint(name = "token_unique", columnNames = "token")
        }
)
public class AppToken {

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(name = "token", nullable = false)
    private String  token;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "local_date_time", nullable = false)
    private LocalDateTime localDateTime;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    public AppToken(String token,
                    String code,
                    LocalDateTime localDateTime,
                    LocalDateTime expiresAt,
                    AppUser appUser) {
        this.token = token;
        this.code = code;
        this.localDateTime = localDateTime;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }
}
