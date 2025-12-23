package com.seapos.webapi.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePermissionModel {

    @NotBlank
    private String roleId;

    public String getRoleId() {
        return roleId;
    }
}
