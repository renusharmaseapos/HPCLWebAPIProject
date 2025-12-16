package com.seapos.webapi.controller;

import com.seapos.webapi.models.*;
import com.seapos.webapi.services.UseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/UserManagement")
public class UserController {
    UseService Service =new  UseService();
    @PostMapping("/ForgetUserPassword")
    public ResponseEntity<?> login(@RequestBody ChangePasswordInput request) {
        var result = Service.forgetUserPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
