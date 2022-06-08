package com.myplaygroup.server.user.controller;

import com.myplaygroup.server.user.request.RegistrationRequest;
import com.myplaygroup.server.user.response.AppUserItem;
import com.myplaygroup.server.user.service.UsersService;
import com.myplaygroup.server.shared.data.SimpleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping
    public List<AppUserItem> getUsers(){
        log.info("api/v1/registration");
        return usersService.getUsers();
    }

    @PostMapping
    public SimpleResponse registerUser(@RequestBody @Valid RegistrationRequest registrationRequest){
        log.info("api/v1/registration");
        return usersService.registerUser(registrationRequest);
    }
}