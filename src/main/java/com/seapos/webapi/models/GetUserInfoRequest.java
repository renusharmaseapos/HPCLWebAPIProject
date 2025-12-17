package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserInfoRequest {
    private int userTypeId;
    private String userName;
     // pagination
//    private int pageNumber;
//    private int pageSize;
}
