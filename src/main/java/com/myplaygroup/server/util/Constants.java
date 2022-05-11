package com.myplaygroup.server.util;

public final class Constants {

    private Constants() {

    }

    // Error message
    public static final String USER_NOT_FOUND_MSG = "username %s not found";

    // Validation Pattern Error
    public static final String USERNAME_VALIDATION_MSG = "Username must be lowercase alphanumeric with no spaces";
    public static final String PASSWORD_VALIDATION_MSG = "Password must be at least 8 letters, contain one number, lowercase and one uppercase letter with no spaces";
    public static final String PHONE_NUMBER_VALIDATION_MSG = "Phone number must be 11 digits";
    public static final String EMAIL_VALIDATION_MSG = "Need to be a valid email";
    public static final String PROFILE_NAME_VALIDATION_MSG =  "Profile Name can't be blank";
    public static final String TOKEN_VALIDATION_MSG =  "Token cannot be empty";
    public static final String CODE_VALIDATION_MSG =  "Code cannot be empty";
    public static final String MESSAGE_VALIDATION_MSG =  "Message cannot be empty";
    public static final String RECEIVERS_VALIDATION_MSG =  "There need to be at least 1 receiver";

    // Validation Pattern Regex
    public static final String USERNAME_VALIDATION_REGEX = "^[a-z\\d_]*$"; // Only lowercase, no spaces
    public static final String PASSWORD_VALIDATION_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"; // At least 1 digit, 1 lowercase, 1 uppercase,no whitespace and min 8 letters
    public static final String PHONE_NUMBER_VALIDATION_REGEX = "^\\d{11}$";  // 11 digits
}
