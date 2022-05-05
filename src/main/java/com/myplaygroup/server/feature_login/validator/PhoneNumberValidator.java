package com.myplaygroup.server.feature_login.validator;

import java.util.function.Predicate;

public class PhoneNumberValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return true;
    }
}
