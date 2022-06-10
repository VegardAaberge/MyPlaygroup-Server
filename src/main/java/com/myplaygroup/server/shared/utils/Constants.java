package com.myplaygroup.server.shared.utils;

public final class Constants {

    private Constants() {}

    public static final String ProfilePath = "files/profile";

    public static final Long AccessTokenValidity = 30 * 24 * 60 * 60 * 1000L; //10 * 60 * 1000L; // 10 minutes
    public static final Long RefreshTokenValidity = 30 * 24 * 60 * 60 * 1000L; // 30 days

    // Error message
    public static final String USER_NOT_FOUND_MSG = "username %s not found";

    // Validation Pattern Error
    public static final String USERNAME_VALIDATION_MSG = "Username must be lowercase alphanumeric with no spaces";
    public static final String PASSWORD_VALIDATION_MSG = "Password must be at least 8 letters, contain one number, lowercase and one uppercase letter with no spaces";
    public static final String PHONE_NUMBER_VALIDATION_MSG = "Phone number must be 11 digits";
    public static final String PROFILE_NAME_VALIDATION_MSG =  "Profile Name can't be blank";
    public static final String DATE_NAME_VALIDATION_MSG =  "Date can't be null";
    public static final String START_TIME_NAME_VALIDATION_MSG =  "start time can't be null";
    public static final String END_TIME_NAME_VALIDATION_MSG =  "end time can't be null";
    public static final String TOKEN_VALIDATION_MSG =  "Token cannot be empty";
    public static final String CODE_VALIDATION_MSG =  "Code cannot be empty";
    public static final String CLIENT_ID_VALIDATION_MSG =  "Id cannot be empty";
    public static final String MESSAGE_VALIDATION_MSG =  "Message cannot be empty";
    public static final String RECEIVERS_VALIDATION_MSG =  "There need to be at least 1 receiver";
    public static final String CLASS_REQUESTS_VALIDATION_MSG =  "There need to be at least 1 daily class";

    // Validation Pattern Regex
    public static final String USERNAME_VALIDATION_REGEX = "^[a-z\\d_]*$"; // Only lowercase, no spaces
    public static final String PASSWORD_VALIDATION_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"; // At least 1 digit, 1 lowercase, 1 uppercase,no whitespace and min 8 letters
    public static final String PHONE_NUMBER_VALIDATION_REGEX = "^\\d{11}$";  // 11 digits

    // Standard Plan Names
    public static final String EVENING_2 = "EVENING_2";
    public static final String EVENING_3 = "EVENING_3";
    public static final String MORNING_2 = "MORNING_2";
    public static final String MORNING_3 = "MORNING_3";
    public static final String MORNING_4 = "MORNING_4";
    public static final String MORNING_5 = "MORNING_5";
    public static final String MORNING_DISCOUNT_5 = "MORNING_DISCOUNT_5";

    public static final Integer EVENING_2_PRICE = 590;
    public static final Integer EVENING_3_PRICE = 790;
    public static final Integer MORNING_2_PRICE = 490;
    public static final Integer MORNING_3_PRICE = 690;
    public static final Integer MORNING_4_PRICE = 790;
    public static final Integer MORNING_5_PRICE = 890;
    public static final Integer MORNING_DISCOUNT_5_PRICE = 790;
}
