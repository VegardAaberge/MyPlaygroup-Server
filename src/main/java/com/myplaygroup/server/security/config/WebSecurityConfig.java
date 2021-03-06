package com.myplaygroup.server.security.config;

import com.myplaygroup.server.user.service.AppUserService;
import com.myplaygroup.server.security.AuthorizationService;
import com.myplaygroup.server.security.filter.CustomAuthenticationFilter;
import com.myplaygroup.server.security.filter.CustomAuthorizationFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.myplaygroup.server.user.model.AppUser.UserRole.ADMIN;
import static com.myplaygroup.server.user.model.AppUser.UserRole.USER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorizationService authorizationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), authorizationService);
        customAuthenticationFilter.setFilterProcessesUrl("/api/v*/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/chat-socket").permitAll();
        http.authorizeRequests().antMatchers("/api/v*/login/**", "/api/v*/reset-password/**").permitAll();
        http.authorizeRequests().antMatchers("/api/v*/chat/**", "/api/v*/profile/**", "api/v1/schedule/**").hasAnyAuthority(ADMIN.name(), USER.name());
        http.authorizeRequests().antMatchers("/api/v*/users/**", "api/v1/classes/**", "api/v1/plans/**").hasAuthority(ADMIN.name());
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(authorizationService), UsernamePasswordAuthenticationFilter.class);
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
