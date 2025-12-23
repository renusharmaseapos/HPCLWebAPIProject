package com.seapos.webapi.controller;

import com.seapos.webapi.models.*;
import com.seapos.webapi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/ChangeUserStatus")
    public ResponseEntity<ApiResponse> changeUserStatus(
            @RequestBody ChangeUserStatusRequest request) {

        return ResponseEntity.ok(
                Service.changeUserStatus(request)
        );
    }

    @GetMapping("GetClientList/{userId}")
    public ResponseEntity<ApiResponse> getClientList(
            @PathVariable("userId") String userId) {

        List<ClientResponseDto> data =
                Service.getClientList(userId);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setStatusCode("200");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("GetRolesByEntityId/{entityTypeId}")
    public ResponseEntity<Map<String, Object>> getRolesByEntityId(
            @PathVariable("entityTypeId") int entityTypeId) {

        List<RoleResponseDto> roles =
                Service.getRolesByEntityTypeId(entityTypeId);

        Map<String, Object> response = new HashMap<>();
        response.put("Data", roles);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/UnlockUser")
    public ResponseEntity<UnlockUserModelOutput> unlockUser(
            @Valid @RequestBody UnlockUserModelInput request
    ) {
        return ResponseEntity.ok(
                Service.unlockUser(request)
        );
    }

    @PostMapping("/AddUserOutside")
    public ResponseEntity<?> AddUserOutside(@RequestBody UserModel userModel) {
        ApiResponse response = new ApiResponse();
        response = Service.addUser(userModel);
        if (!response.getStatus())
            return ResponseEntity.status(500).body(response);
        else
            return ResponseEntity.ok(response);
    }

    @PostMapping("/getRolePermissions")
    public ResponseEntity<RolePermissions> getRolePermissions(
            @RequestBody UserPrivilegeSearch request
    ) {
        return ResponseEntity.ok(
                Service.getRolePermissions(request)
        );
    }

    @PostMapping("/AddUserRole")
    public ResponseEntity<ApiResponseModel> addRole(
            @RequestBody AddRoleRequest request) {

        String message = Service.addOrUpdateRole(request);

        ApiResponseModel response = new ApiResponseModel();
        response.setStatus(true);
        response.setChangeRequest(false);
        response.setSuccessMessage(message);
        response.setErrorMessage(null);
        response.setRedirectURL(null);
        response.setJsonRequest(null);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/GetUserRoleList")
    public ResponseEntity<UserRoleList> getUserRoleList(
            @RequestBody UserRoleSearch request) {

        UserRoleList response = Service.getUserRoleList(request);
        return ResponseEntity.ok(response);
    }
}


