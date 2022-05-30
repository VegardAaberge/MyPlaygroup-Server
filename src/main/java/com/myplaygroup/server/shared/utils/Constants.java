package com.myplaygroup.server.shared.utils;

public final class Constants {

    private Constants() {}

    public static final String ProfilePath = "files/profile";

    public static final Long AccessTokenValidity = 10 * 60 * 1000L; // 10 minutes
    public static final Long RefreshTokenValidity = 30 * 24 * 60 * 60 * 1000L; // 30 days

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
    public static final String CLIENT_ID_VALIDATION_MSG =  "Id cannot be empty";
    public static final String MESSAGE_VALIDATION_MSG =  "Message cannot be empty";
    public static final String RECEIVERS_VALIDATION_MSG =  "There need to be at least 1 receiver";

    // Validation Pattern Regex
    public static final String USERNAME_VALIDATION_REGEX = "^[a-z\\d_]*$"; // Only lowercase, no spaces
    public static final String PASSWORD_VALIDATION_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"; // At least 1 digit, 1 lowercase, 1 uppercase,no whitespace and min 8 letters
    public static final String PHONE_NUMBER_VALIDATION_REGEX = "^\\d{11}$";  // 11 digits

    // Standard Plan Names
    public static final String EVENING_GROUP_2DAYS = "Evening Group 2 days";
    public static final String EVENING_GROUP_3DAYS = "Evening Group 3 days";
    public static final String MORNING_GROUP_2DAYS = "Morning Group 2 days";
    public static final String MORNING_GROUP_3DAYS = "Morning Group 3 days";
    public static final String MORNING_GROUP_4DAYS = "Morning Group 4 days";
    public static final String MORNING_GROUP_5DAYS = "Morning Group 5 days";
    public static final String MORNING_GROUP_5DAYS_DISCOUNT = "Morning Group 5 days (Discount)";

    public static final Integer EVENING_GROUP_2DAYS_PRICE = 590;
    public static final Integer EVENING_GROUP_3DAYS_PRICE = 790;
    public static final Integer MORNING_GROUP_2DAYS_PRICE = 490;
    public static final Integer MORNING_GROUP_3DAYS_PRICE = 690;
    public static final Integer MORNING_GROUP_4DAYS_PRICE = 790;
    public static final Integer MORNING_GROUP_5DAYS_PRICE = 890;
    public static final Integer MORNING_GROUP_5DAYS_DISCOUNT_PRICE = 790;
}
