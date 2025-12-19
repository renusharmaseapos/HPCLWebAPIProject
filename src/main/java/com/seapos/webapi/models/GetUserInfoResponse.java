package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetUserInfoResponse {
    private List<GetUserInfo> data;
    private String encData;
    private int totalCount;
}
