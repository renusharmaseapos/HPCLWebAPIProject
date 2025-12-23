package com.seapos.webapi.models;
import lombok.Data;

@Data
public class UserPrivilegeSearch {

    private String roleId;
    private String username;
    private String menuId;
    private String menuName;
}
