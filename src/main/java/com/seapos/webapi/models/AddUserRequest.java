 package com.seapos.webapi.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddUserRequest {

    @NotBlank(message = "First Name is Required.")
    private String firstName;
    @NotBlank(message = "Last Name is Required.")
    private String lastName;
    @NotBlank(message = "Email is Required.")
    private String email;
    @NotBlank(message = "Mobile is Required.")
    private String mobile;

    private Integer entityTypeId;
    private Integer entityId;

    private String roleId;
    private Integer userId;
    private String clientIds;
    private Integer userStatus;
    private Integer createdBy;
}