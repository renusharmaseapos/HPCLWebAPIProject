package com.seapos.webapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubMenu {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Order")
    private int order;
    @JsonProperty("Page")
    private List<Page> page = new ArrayList<>();
}