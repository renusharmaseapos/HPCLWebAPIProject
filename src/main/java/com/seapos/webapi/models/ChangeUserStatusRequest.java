package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeUserStatusRequest {
    private Integer userStatusId;
    private Integer entityUserId;
    private String remarks;
    private String userEmail;
    private String attachments;
    private String pageName;
    private String userName;
}
