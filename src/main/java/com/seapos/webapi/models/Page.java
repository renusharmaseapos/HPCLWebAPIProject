package com.seapos.webapi.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    @JsonProperty("ControlId")
    private int controlId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Route")
    private String route;
    @JsonProperty("Add")
    private int add;
    @JsonProperty("AddNeedApproval")
    private int addNeedApproval;
    @JsonProperty("AddCanApprove")
    private int addCanApprove;
    @JsonProperty("Update")
    private int update;
    @JsonProperty("UpdateNeedApproval")
    private int updateNeedApproval;
    @JsonProperty("UpdateCanApprove")
    private int updateCanApprove;
    @JsonProperty("Delete")
    private int delete;
    @JsonProperty("DeleteCanApprove")
    private int deleteCanApprove;
    @JsonProperty("DeleteNeedApproval")
    private int deleteNeedApproval;
    @JsonProperty("View")
    private int view;
}

