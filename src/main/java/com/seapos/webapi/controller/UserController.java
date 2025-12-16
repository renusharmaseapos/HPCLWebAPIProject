package com.seapos.webapi.controller;

import com.seapos.webapi.models.request.AddUserRequest;
import com.seapos.webapi.models.response.ApiResponseModel;
import com.seapos.webapi.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/UserManagement")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("AddUser")
    public ResponseEntity<ApiResponseModel<Void>>addUser(
            @Valid @RequestBody AddUserRequest request) {

        ApiResponseModel<Void> response = userService.addOrUpdateUser(request);
        return ResponseEntity.ok(response);
    }
}
