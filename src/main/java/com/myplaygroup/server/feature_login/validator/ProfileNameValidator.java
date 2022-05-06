package com.myplaygroup.server.feature_login.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class ProfileNameValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return true;
    }
}
