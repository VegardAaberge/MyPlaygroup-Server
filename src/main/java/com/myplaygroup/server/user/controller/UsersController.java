package com.myplaygroup.server.user.controller;

import com.myplaygroup.server.user.request.AppUserRequest;
import com.myplaygroup.server.user.response.AppUserItem;
import com.myplaygroup.server.user.service.UsersService;
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
        log.info("api/v1/users");
        return usersService.getUsers();
    }

    @PostMapping
    public List<AppUserItem> registerUser(@RequestBody @Valid List<AppUserRequest> registrationRequest){
        log.info("api/v1/users");
        return usersService.registerUsers(registrationRequest);
    }
}
