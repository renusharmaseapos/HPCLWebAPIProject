package com.seapos.webapi.Utility;

public enum MembershipCreateStatus {
    // The user was successfully created.
    SUCCESS,

    // The user name was not found in the database.
    INVALID_USER_NAME,

    // The password is not formatted correctly.
    INVALID_PASSWORD,

    // The password question is not formatted correctly.
    INVALID_QUESTION,

    // The password answer is not formatted correctly.
    INVALID_ANSWER,

    // The email address is not formatted correctly.
    INVALID_EMAIL,

    // The user name already exists in the database for the application.
    DUPLICATE_USER_NAME,

    // The email address already exists in the database for the application.
    DUPLICATE_EMAIL,

    // The user was not created, for a reason defined by the provider.
    USER_REJECTED,

    // The provider user key is of an invalid type or format.
    INVALID_PROVIDER_USER_KEY,

    // The provider user key already exists in the database for the application.
    DUPLICATE_PROVIDER_USER_KEY,

    // The provider returned an error that is not described by other MembershipCreateStatus enumeration values.
    PROVIDER_ERROR
}
