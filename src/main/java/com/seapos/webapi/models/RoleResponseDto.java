package com.seapos.webapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {

    @JsonProperty("RoleId")
    private String roleId;

    @JsonProperty("RoleName")
    private String roleName;

    @JsonProperty("IsDefaultRole")
    private Boolean isDefaultRole;
}

