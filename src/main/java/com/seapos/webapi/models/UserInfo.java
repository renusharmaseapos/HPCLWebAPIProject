package com.seapos.webapi.models;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class UserInfo  {

    private String providerUserKey;
    private Integer userID;
    private String userName;
    private String email;
    private LocalDateTime lastLoginDate;
    private Boolean isApproved;
    private String userIp;
    private String userRoles;
    private LocalDateTime sessionEndTime;
    private Integer entityTypeId;
    private LocalDateTime lastActivityDate;
    private LocalDateTime createDate;
    private Integer entityId;
    private Integer numericUserId;
    private Boolean isLoggedIn;
}
