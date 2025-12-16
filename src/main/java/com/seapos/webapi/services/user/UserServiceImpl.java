package com.seapos.webapi.services.user;

import com.seapos.webapi.dataaccess.user.UserRepository;
import com.seapos.webapi.models.request.AddUserRequest;
import com.seapos.webapi.models.response.ApiResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponseModel<Void> addOrUpdateUser(AddUserRequest request) {

        ApiResponseModel<Void> response = new ApiResponseModel<Void>();

        String dbMessage = userRepository.addOrUpdateUser(request);

        response.setStatus(true);
        response.setSuccessMessage(dbMessage);

        return response;
    }
}
