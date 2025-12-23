package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddRoleRequest {

    private String applicationName;
    private String roleId;          // null = insert
    private String roleName;
    private String description;
    private int entityTypeId;
    private boolean isDefault;
    private boolean active;
    private int userId;

    private List<RolePermissionRequest> permissions;
}
