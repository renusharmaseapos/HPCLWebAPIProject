package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleSearch {

    private String roleId;       // nullable
    private String roleName;     // nullable
    private String userTypeId;   // nullable (EntityTypeId as String)
    private int skipCount;       // pagination
    private int pageSize;        // pagination
}