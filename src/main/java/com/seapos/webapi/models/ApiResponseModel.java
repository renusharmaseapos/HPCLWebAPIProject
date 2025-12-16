package com.seapos.webapi.models;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseModel {
    private boolean status;
    private boolean changeRequest;
    private String successMessage;
    private String errorMessage;
    private String redirectURL;
    private String jsonRequest;
}
