package com.myplaygroup.server.user.controller;

import com.myplaygroup.server.other.SimpleResponse;
import com.myplaygroup.server.security.AuthorizationService;
import com.myplaygroup.server.user.request.EditProfileRequest;
import com.myplaygroup.server.user.request.UpdateProfileRequest;
import com.myplaygroup.server.user.response.EditProfileResponse;
import com.myplaygroup.server.user.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/profile")
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private AuthorizationService authorizationService;

    @PostMapping(path = "create/{username}")
    public EditProfileResponse updateProfile(
            @PathVariable("username") String username,
            @RequestBody @Valid UpdateProfileRequest request
    ) {
        return profileService.updateProfile(username, request);
    }

    @PostMapping(path = "edit/{username}")
    public EditProfileResponse editProfile(
            @PathVariable("username") String username,
            @RequestBody @Valid EditProfileRequest request
    ) {
        return profileService.editProfile(username, request);
    }

    @GetMapping(path = "get-image/{username}")
    public ResponseEntity<Resource> uploadProfileImage(
            @PathVariable("username") String username
    ) {
        Resource profileImage = profileService.getProfileImage(username);

        String contentType = "application/octet-stream";
        String headerValue = "attachment filename=\"" + profileImage.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(profileImage);
    }

    @PostMapping(path = "upload-image")
    public SimpleResponse getProfileImage(
            @RequestParam("image") MultipartFile file,
            HttpServletRequest servletRequest
    ) {
        String username = authorizationService.getUserInfoFromRequest(servletRequest).getUsername();

        return profileService.uploadProfileImage(username, file);
    }
}
