package com.seapos.webapi.controller;

import com.seapos.webapi.models.*;
import com.seapos.webapi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/UserManagement")
@RequiredArgsConstructor
public class UserController {
    //UserService Service =new UserService();
    private final UserService Service;

    @PostMapping("/ForgetUserPassword")
    public ResponseEntity<?> ForgetUserPassword(@RequestBody ChangePasswordInput request) {
        var result = Service.forgetUserPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/AddUser")
    public ResponseEntity<ApiResponseModel> addUser(
            @Valid @RequestBody AddUserRequest request) {

        ApiResponseModel response = Service.addOrUpdateUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/GetUserInformation")
    public ResponseEntity<GetUserInfoResponse> getUserInfoList(
            @RequestBody GetUserInfoRequest request) {

        GetUserInfoResponse response = Service.getUserInfoList(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/GetUserInfoList")
    public ResponseEntity<GetUserInfoResponse> getUserInfoEditList(
            @RequestBody UserInfoSearch request) {

        GetUserInfoResponse response = Service.getUserInfoEditList(request);
        return ResponseEntity.ok(response);
    }
    //    @PostMapping("/addUser")
//    public ResponseEntity<?> addUser(@RequestBody UserModel userModel) {
//        ApiResponse response = new ApiResponse();
//        response = Service.addUser(userModel);
//        if (!response.getStatus())
//            return ResponseEntity.status(500).body(response);
//        else
//            return ResponseEntity.ok(response);
//    }

    @PostMapping("/AddUserOutside")
    public ResponseEntity<?> AddUserOutside(@RequestBody UserModel userModel) {
        ApiResponse response = new ApiResponse();
        response = Service.addUser(userModel);
        if (!response.getStatus())
            return ResponseEntity.status(500).body(response);
        else
            return ResponseEntity.ok(response);
    }

}


