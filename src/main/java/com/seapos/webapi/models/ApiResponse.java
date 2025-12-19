package com.seapos.webapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiResponse {

    private Boolean status;
    private String statusCode;
    private String successMessage;
    private String errorMessage;
    private Object data;
    private String message;



    public static ApiResponse success(String msg) {
        ApiResponse r = new ApiResponse();
        r.setStatus(true);
        r.setStatusCode("200");
        r.setMessage(msg);
        return r;
    }

    public static ApiResponse failure(String msg) {
        ApiResponse r = new ApiResponse();
        r.setStatus(false);
        r.setStatusCode("500");
        r.setMessage(msg);
        return r;
    }
}

