package com.seapos.webapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoSearch {
    private Integer userId;
    @Schema(hidden = true)
    private String roleTypeId;

    @Schema(hidden = true)
    private String userTypeId;

    @Schema(hidden = true)
    private String username;

    @Schema(hidden = true)
    private String email;

    @Schema(hidden = true)
    private String externalId;

    @Schema(hidden = true)
    private Integer skipCount;

    @Schema(hidden = true)
    private Integer pageSize;
}
