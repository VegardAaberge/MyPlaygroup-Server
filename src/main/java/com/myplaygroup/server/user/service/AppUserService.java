package com.myplaygroup.server.user.service;

import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.shared.utils.Constants;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.myplaygroup.server.user.model.AppUser.UserRole.*;
import static com.myplaygroup.server.shared.utils.Constants.USER_NOT_FOUND_MSG;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    @Bean
    CommandLineRunner AppUserServiceLineRunner(AppUserRepository appUserRepository){
        return args -> {
            boolean isAdminPresent = appUserRepository.findByUsername("admin").isPresent();
            if(!isAdminPresent){
                AppUser admin = new AppUser(
                        "admin",
                        bCryptPasswordEncoder.encode(Constants.MY_PLAYGROUP), // Need to be changed
                        ADMIN
                );
                admin.setProfileName("Playgroup");
                admin.setPhoneNumber("13900204214");
                admin.setProfileCreated(true);
                appUserRepository.save(admin);

                AppUser guest = new AppUser(
                        "guest",
                        bCryptPasswordEncoder.encode(Constants.MY_PLAYGROUP),
                        USER
                );
                appUserRepository.save(guest);
            }
        };
    }
}
