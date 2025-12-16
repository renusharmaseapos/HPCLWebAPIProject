package com.seapos.webapi.controller;

import com.seapos.webapi.models.*;
import com.seapos.webapi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Service;
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
    public ResponseEntity<?> login(@RequestBody ChangePasswordInput request) {
        var result = Service.forgetUserPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PostMapping("/AddUser")
    public ResponseEntity<ApiResponseModel>addUser(
            @Valid @RequestBody AddUserRequest request) {

        ApiResponseModel  response = Service.addOrUpdateUser(request);
        return ResponseEntity.ok(response);
    }

}
