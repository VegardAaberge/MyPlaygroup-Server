package com.myplaygroup.server.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static com.myplaygroup.server.shared.utils.Constants.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "app_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "username_unique", columnNames = "username"),
                @UniqueConstraint(name = "email_unique", columnNames = "email")
        }
)
public class AppUser implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "app_user_sequence",
            sequenceName = "app_user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_user_sequence"
    )
    @Column(name= "id", updatable = false)
    private long id;

    @NotBlank(message = CLIENT_ID_VALIDATION_MSG)
    @Column(name = "client_id", updatable = false, nullable = false)
    private String clientId = UUID.randomUUID().toString();

    @Pattern(regexp=USERNAME_VALIDATION_REGEX, message=USERNAME_VALIDATION_MSG)
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "profile_name")
    private String profileName;

    @Pattern(regexp = PHONE_NUMBER_VALIDATION_REGEX, message = PHONE_NUMBER_VALIDATION_MSG)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Email(message = EMAIL_VALIDATION_MSG)
    @Column(name = "email")
    private String email;

    @Pattern(regexp = PASSWORD_VALIDATION_REGEX, message = PASSWORD_VALIDATION_MSG)
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_user_role", nullable = false)
    private UserRole appUserRole;

    @Column(name = "user_credit", nullable = false)
    private Long userCredit = 0L;

    @Column(name = "locked", nullable = false)
    private Boolean locked = false;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "profile_created", nullable = false)
    private Boolean profileCreated = false;

    public AppUser(String username,
                   String password,
                   UserRole appUserRole) {
        this.username = username;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());

        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public enum UserRole {
        USER,
        ADMIN
    }
}
