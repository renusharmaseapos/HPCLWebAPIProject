package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePermissionRequest {

    private int controlId;

    private int add;
    private int addNeedApproval;
    private int addCanApprove;

    private int update;
    private int updateNeedApproval;
    private int updateCanApprove;

    private int delete;
    private int deleteNeedApproval;
    private int deleteCanApprove;

    private int view;
}

