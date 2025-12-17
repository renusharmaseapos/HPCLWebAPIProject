package com.seapos.webapi.controller;


import com.seapos.webapi.models.*;
import com.seapos.webapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("api/UserManagement")
public class UserController {
    UserService Service =new UserService();
    @PostMapping("/ForgetUserPassword")
    public ResponseEntity<?> ForgetUserPassword(@RequestBody ChangePasswordInput request) {
        var result = Service.forgetUserPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
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

    }



