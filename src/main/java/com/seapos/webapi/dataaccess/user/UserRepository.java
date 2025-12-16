package com.seapos.webapi.dataaccess.user;

import com.seapos.webapi.models.request.AddUserRequest;
import com.seapos.webapi.models.response.ApiResponseModel;

public interface UserRepository {
    String addOrUpdateUser(AddUserRequest request);
}
