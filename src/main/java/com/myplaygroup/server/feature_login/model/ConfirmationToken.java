package com.myplaygroup.server.feature_login.model;

import javax.persistence.*;
import java.time.LocalDateTime;

public class ConfirmationToken {

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

    @Column(nullable = false)
    private String  token;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    private LocalDateTime confirmedAt;

    public ConfirmationToken(String token,
                             LocalDateTime localDateTime,
                             LocalDateTime expiresAt,
                             AppUser appUser) {
        this.token = token;
        this.localDateTime = localDateTime;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }
}
