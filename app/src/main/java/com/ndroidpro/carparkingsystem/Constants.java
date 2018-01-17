package com.ndroidpro.carparkingsystem;

public class Constants {
    public static final String INTENT_PAYMENT = "com.ndroidpro.carparkingsystem.INTENT_PAYMENT";
    public static final String INTENT_EDIT_CAR_PARKING_LOCATION = "com.ndroidpro.carparkingsystem.INTENT_EDIT_CAR_PARKING_LOCATION";
    public static final String INTENT_CAR_PARKING_LOCATION_DATA = "com.ndroidpro.carparkingsystem.INTENT_CAR_PARKING_LOCATION_DATA";


    public static final String DB_USERS = "users";
    public static final String DB_PROFILE = "user_profile";

    public static final String DB_CAR_PARKING_LOCATION_LIST = "car_parking_location_list";
    public static final String DB_NOTIFICATION_REQUESTS = "notification_requests";

    // 1 for Super Admin , 2 for customer
    public static final int USER_ROLE_ADMIN = 1;
    public static final int USER_ROLE_CUSTOMER = 2;
}

