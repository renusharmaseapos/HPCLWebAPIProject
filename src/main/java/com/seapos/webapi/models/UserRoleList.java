package com.seapos.webapi.models;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UserRoleList {

    private List<UserRoleData> data;
    private int totalCount;
}
