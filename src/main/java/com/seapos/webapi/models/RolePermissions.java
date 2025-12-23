package com.seapos.webapi.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RolePermissions {

    @JsonProperty("RoleId")
    private String roleId;
    @JsonProperty("RoleName")
    private String roleName;
    @JsonProperty("RoleDescription")
    private String roleDescription;
    @JsonProperty("EntityTypeId")
    private int entityTypeId;
    @JsonProperty("IsDefault")
    private boolean isDefault;

    // IMPORTANT: mutable list (no immutable issue)
    @JsonProperty("Menu")
    private List<Menu> menu = new ArrayList<>();
}
