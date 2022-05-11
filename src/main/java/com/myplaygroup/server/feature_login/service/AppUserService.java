package com.myplaygroup.server.feature_login.service;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.myplaygroup.server.feature_login.model.AppUser.UserRole.*;
import static com.myplaygroup.server.util.Constants.USER_NOT_FOUND_MSG;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
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
                        bCryptPasswordEncoder.encode("123"), // Need to be changed
                        ADMIN
                );
                appUserRepository.save(admin);

                AppUser vegard = new AppUser(
                        "vegard",
                        bCryptPasswordEncoder.encode("123"), // Need to be changed
                        USER
                );
                appUserRepository.save(vegard);

                AppUser meng = new AppUser(
                        "meng",
                        bCryptPasswordEncoder.encode("123"), // Need to be changed
                        USER
                );
                appUserRepository.save(meng);
            }
        };
    }
}
