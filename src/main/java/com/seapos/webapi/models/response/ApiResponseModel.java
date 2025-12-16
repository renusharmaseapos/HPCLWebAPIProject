package com.seapos.webapi.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseModel <T> {

    private boolean status;
    private boolean changeRequest;
    private String successMessage;
    private String errorMessage;
    private String redirectURL;
    private String jsonRequest;
}
