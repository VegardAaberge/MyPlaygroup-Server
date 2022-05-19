package com.myplaygroup.server.user.service;

import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.exception.ServerErrorException;
import com.myplaygroup.server.other.DocumentServiceImpl;
import com.myplaygroup.server.other.SimpleResponse;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.repository.AppUserRepository;
import com.myplaygroup.server.user.request.EditProfileRequest;
import com.myplaygroup.server.user.request.UpdateProfileRequest;
import com.myplaygroup.server.user.response.EditProfileResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.myplaygroup.server.other.Constants.ProfilePath;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private final AppUserRepository appUserRepository;

    private final AppUserService appUserService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private DocumentServiceImpl documentService;

    public EditProfileResponse updateProfile(String username, UpdateProfileRequest request) {

        AppUser appUser = appUserService.loadUserByUsername(username);

        String encodedPassword = bCryptPasswordEncoder.encode(request.password);

        appUser.setPassword(encodedPassword);
        appUser.setProfileName(request.profileName);
        appUser.setPhoneNumber(request.phoneNumber);
        appUser.setEmail(request.email);
        appUser.setProfileCreated(true);

        appUserRepository.save(appUser);

        return new EditProfileResponse(
                username,
                appUser.getProfileName(),
                appUser.getPhoneNumber(),
                appUser.getEmail()
        );
    }

    public EditProfileResponse editProfile(String username, EditProfileRequest request) {
        AppUser appUser = appUserService.loadUserByUsername(username);
        if (!appUser.getProfileCreated()) {
            throw new IllegalStateException("User profile hasn't been created");
        }

        if (request.password != null) {
            String encodedPassword = bCryptPasswordEncoder.encode(request.password);
            appUser.setPassword(encodedPassword);
        }

        if (request.profileName != null) {
            appUser.setProfileName(request.profileName);
        }

        if (request.email != null) {
            appUser.setEmail(request.email);
        }

        if (request.phoneNumber != null) {
            appUser.setPhoneNumber(request.phoneNumber);
        }

        appUserRepository.save(appUser);

        return new EditProfileResponse(
                username,
                appUser.getProfileName(),
                appUser.getPhoneNumber(),
                appUser.getEmail()
        );
    }

    public Resource getProfileImage(String username) {

        Path profileDir = documentService.getProfileStorageLocation();

        return getMostRecentProfileImage(profileDir, username);
    }

    public SimpleResponse uploadProfileImage(String username, MultipartFile file) {

        String filename = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + "." + username;

        try(InputStream inputStream = file.getInputStream()) {
            Path filePath = documentService.getProfileStorageLocation().resolve(filename);
            log.error("Copy file to " + filePath.toAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioe) {
            log.error("Error saving uploaded file", ioe);
            throw new ServerErrorException("Error saving uploaded file");
        }

        return new SimpleResponse("Success");
    }

    Resource getMostRecentProfileImage(Path profileDir, String username) {
        try {
            List<Path> profileImages = Files.list(profileDir).filter(file ->
                    file.getFileName().toString().endsWith(username)
            ).collect(Collectors.toList());

            if(profileImages.size() == 0){
                throw new NotFoundException("No profile image");
            }

            Path profileImage = profileImages.get(profileImages.size() - 1);
            log.error("Get profile image " + profileImage.toAbsolutePath());

            return new UrlResource(profileImage.toUri());

        }catch (IOException ioe) {
            log.error("Error getting uploaded file", ioe);
            throw new ServerErrorException("Error getting uploaded file");
        }
    }
}
