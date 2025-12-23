package com.seapos.webapi.models;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UserRoleData {

    private String applicationName;
    private String roleId;
    private String roleName;
    private String description;
    private String userType;
    private int entityTypeId;
    private int userId;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
    private boolean isDefault;
    private boolean active;
    private String status;
    private String commandType;

    private List<Page> permissions; // reuse existing Page DTO
}
