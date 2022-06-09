package com.myplaygroup.server.user.service;

import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.repository.AppUserRepository;
import com.myplaygroup.server.user.request.RegistrationRequest;
import com.myplaygroup.server.shared.data.SimpleResponse;
import com.myplaygroup.server.user.response.AppUserItem;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.myplaygroup.server.user.model.AppUser.UserRole.USER;

@Service
@AllArgsConstructor
public class UsersService {

    private final AppUserRepository appUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<AppUserItem> getUsers() {
        return appUserRepository.getAllUsers();
    }

    public List<AppUserItem> registerUsers(List<RegistrationRequest> requests) {

        List<AppUser> appUsers = requests.stream().map(request -> {
            Optional<AppUser> appUserOptional = appUserRepository.findByUsername(request.username);

            if(appUserOptional.isPresent()){
                if(appUserOptional.get().isEnabled()){
                    throw new IllegalStateException("username already taken");
                }
            }

            String encodedPassword = bCryptPasswordEncoder.encode(request.password);

            return new AppUser(
                    request.username,
                    encodedPassword,
                    USER
            );
        }).collect(Collectors.toList());

        appUserRepository.saveAll(appUsers);

        return getUsers();
    }
}
