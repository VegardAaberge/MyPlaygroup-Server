package com.myplaygroup.server.feature_login.service;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.myplaygroup.server.util.Constants.USER_NOT_FOUND_MSG;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    @Bean
    CommandLineRunner commandLineRunner(AppUserRepository appUserRepository){
        return args -> {
            Boolean isAdminPresent = appUserRepository.findByUsername("admin").isPresent();
            if(!isAdminPresent){
                AppUser admin = new AppUser(
                        "admin",
                        "123", // Need to be changed
                        AppUser.UserRole.ADMIN
                );
                appUserRepository.save(admin);
            }
        };

    }
}
