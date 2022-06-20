package com.myplaygroup.server.user.service;

import com.myplaygroup.server.shared.utils.Constants;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.repository.AppUserRepository;
import com.myplaygroup.server.user.request.AppUserRequest;
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

    public List<AppUserItem> registerUsers(List<AppUserRequest> requests) {

        requests.forEach(request -> {
            if(request.id == -1L){
                registerNewUser(request.username);
            }

            modifyExistingUser(request);
        });

        return getUsers();
    }

    public void registerNewUser(String username){

        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);

        if(appUserOptional.isPresent()){
            throw new IllegalStateException("username already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(Constants.MY_PLAYGROUP);

        appUserRepository.save(new AppUser(
                username,
                encodedPassword,
                USER
        ));
    }

    public void modifyExistingUser(AppUserRequest request){

        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(request.username);

        if(appUserOptional.isEmpty()){
            throw new IllegalStateException("username not found");
        }

        AppUser appUser = appUserOptional.get();
        if(request.resetPassword){
            String encodedPassword = bCryptPasswordEncoder.encode(Constants.MY_PLAYGROUP);
            appUser.setPassword(encodedPassword);
        }

        if(request.phoneNumber != null && !request.phoneNumber.isBlank()){
            appUser.setPhoneNumber(request.phoneNumber);
        }

        if(request.profileName != null && !request.profileName.isBlank()){
            appUser.setProfileName(request.profileName);
        }

        appUser.setLocked(request.locked);
        appUser.setProfileCreated(request.profileCreated);

        appUserRepository.save(appUser);
    }
}
