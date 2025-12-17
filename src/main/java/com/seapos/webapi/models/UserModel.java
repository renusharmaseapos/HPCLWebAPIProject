package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModel {
    private int entityTypeId;
    private long entityCode;
    private int defaultLanguageId;
    private String userName;
    private String email;
    private String password;
    private String confirmPassword;
    private int secretQuestionId;
    private String otherQuestion;
    private String secretQuestionAnswer;
    private boolean isApproved;
    private String userRole;
    private String lastLoginDate;
    private int numericUserId;
    private String acquirerUserName;
    private String acquirerPassword;
    private String acquirerUserRole;
    private String roleId;
    private String externalId;
    private int entityUserId;
}
