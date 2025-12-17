package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserInfo {
    private int entityUserId;
    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String userType;
    private String userStatus;
    private String mobileNo;
    private int entityTypeId;
    private String roleId;
    private int isLockedOut;
}
