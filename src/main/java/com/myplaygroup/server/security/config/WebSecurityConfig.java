package com.myplaygroup.server.security.config;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.myplaygroup.server.feature_login.model.AppUser.UserRole.ADMIN;
import static com.myplaygroup.server.feature_login.model.AppUser.UserRole.USER;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v*/registration/update/**").hasAnyAuthority(ADMIN.name(), USER.name())
                .antMatchers(HttpMethod.POST,"/api/v*/registration/**").hasAuthority(ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/v*/login/**").permitAll()
                .antMatchers("/api/v*/reset-password/**").permitAll()
                .anyRequest()
                .authenticated().and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(dooAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider dooAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
}
