package com.seapos.webapi.services.user;

import com.seapos.webapi.models.request.AddUserRequest;
import com.seapos.webapi.models.response.ApiResponseModel;

public interface UserService {
    ApiResponseModel<Void> addOrUpdateUser(AddUserRequest request);
}
