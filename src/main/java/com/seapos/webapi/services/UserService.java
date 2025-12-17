package com.seapos.webapi.services;
import com.seapos.webapi.Utility.MembershipCreateStatus;
import com.seapos.webapi.models.*;
import com.seapos.webapi.dataaccess.UserDataAccess;
//import com.seapos.webapi.models.AppProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
//import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import com.seapos.webapi.Utility.MembershipCreateStatus;
@Service
public class UserService {


    UserDataAccess dataAccess= new UserDataAccess();
    public MembershipUserCustom GetUser(String UserName){
        return dataAccess.GetUser(UserName);
    }
    public EntityUser validateUserLogin(String username, String password) {

        EntityUser entityUser = dataAccess.GetUserbyName(username);
        entityUser.setMessage("");
        boolean validateUser=false;
        if (entityUser == null) {
            return null;
        }
        int failedPasswordAttemptCount;
        PassDetail Psdetail = validateUser(username, password);
        HttpServletRequest request ;
        String ipAddress = "";// request.getRemoteAddr();
        if (Psdetail.isPasswordvalid==1)
        {
            failedPasswordAttemptCount = Psdetail.getFailedPasswordAttemptCount();
            dataAccess.UnlockUser(username);
            validateUser=true;
        }
        else
        {
            entityUser.setMessage("Invalid username or password");
        }
        if (validateUser) {
            if (entityUser.getEntityTypeId() == 100) // System
            {
                entityUser.setRedirectURl("a");
            }
            else if (entityUser.getEntityTypeId() == 102 || entityUser.getEntityTypeId() == 1) // merchant
            {
                dataAccess.AddLoginUserTrailDetails(username,Psdetail.getUserId(),ipAddress,UUID.randomUUID().toString(),true,LocalDateTime.now());
            }

            return entityUser;
        } else {
            return entityUser;
        }
    }

    public  PassDetail validateUser(String userName, String password) {
        try {
            PassDetail passwordDetail = dataAccess.GetPassworddetails(userName);

            if (passwordDetail == null) {
                return null;
            }
            String dbPassword;

            // PasswordFormat == 1 means hashed, otherwise plain
            if (passwordDetail.getPasswordFormat() == 1) {
                dbPassword = hashPassword(password, passwordDetail.getPasswordSalt());
            } else {
                dbPassword = password;
            }

            if (passwordDetail.getPassword().equals(dbPassword)) {
                passwordDetail.setIsPasswordvalid(1);
                dataAccess.UpdateUserInfo(
                        userName,
                        true,
                        true,
                        3,
                        passwordDetail.getLastLoginDate(),
                        passwordDetail.getLastActivityDate()
                );
            } else {
                passwordDetail.setIsPasswordvalid(0);
                // Update failed login attempt
                dataAccess.UpdateUserInfo(
                        userName,
                        false,    // isApproved
                        true,    // isLockedOut
                        3,        // failureCount?
                        passwordDetail.getLastLoginDate(),
                        passwordDetail.getLastActivityDate()
                );
            }

            return passwordDetail;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String hashPassword(String passWord, String saltValue) throws Exception {
        byte[] bIn = passWord.getBytes(StandardCharsets.UTF_16LE);

        // Convert.FromBase64String(salt)
        byte[] bSalt = Base64.getDecoder().decode(saltValue);

        // Concatenate salt + password
        byte[] bAll = new byte[bSalt.length + bIn.length];
        System.arraycopy(bSalt, 0, bAll, 0, bSalt.length);
        System.arraycopy(bIn, 0, bAll, bSalt.length, bIn.length);

        // SHA1 hash (NOT HMAC)
        MessageDigest sha1 = MessageDigest.getInstance("SHA512");
        byte[] bRet = sha1.digest(bAll);

        // Convert.ToBase64String
        return Base64.getEncoder().encodeToString(bRet);

//        byte[] saltBytes = saltValue.getBytes(StandardCharsets.UTF_16LE);
//        byte[] passwordBytes = passWord.getBytes(StandardCharsets.UTF_16LE);
//
//        // Concatenate salt + password
//        byte[] saltedPass = new byte[saltBytes.length + passwordBytes.length];
//        System.arraycopy(saltBytes, 0, saltedPass, 0, saltBytes.length);
//        System.arraycopy(passwordBytes, 0, saltedPass, saltBytes.length, passwordBytes.length);
//
//        // SHA1 hash (NOT HMAC)
//        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
//        byte[] sha1HashedPass = sha1.digest(saltedPass);
//
//        // Base64 encode result
//        return Base64.getEncoder().encodeToString(sha1HashedPass);
    }


    public  ResponseBase forgetUserPassword(ChangePasswordInput objPassword) {

        ResponseBase result = new ResponseBase();

        // Ensure password and confirm password are the same
        if (!objPassword.getNewPassword().equals(objPassword.getConfirmPassword())) {
            result.setResponseCode("0");
            result.setResponseMessage("Password and Confirm Password do not match.");
            return result;
        }

        // Get the user from membership system
        MembershipUserCustom user = dataAccess.GetUser(objPassword.getUsername());

        if (user == null) {
            result.setResponseCode("0");
            result.setResponseMessage("User not found.");
            return result;
        }

        // Check if user is locked
        if (user.isIsLockedOut()) {
            result.setResponseCode("0");
            result.setResponseMessage("User account is locked. Kindly unlock the user.");
            return result;
        }

        try {
            PassDetail passwordDetail = dataAccess.GetPassworddetails(objPassword.getUsername());
            String salt = generateSalt();
            String HashedPass="";
            if(passwordDetail.getPasswordFormat() == 1)
                HashedPass=hashPassword(objPassword.getNewPassword(),salt);
            else
                HashedPass=objPassword.getNewPassword();

            // Change password
            String output = dataAccess.ChangePassword(objPassword.getUsername(),HashedPass,salt, String.valueOf(passwordDetail.getPasswordFormat()));

            if (Objects.equals(output, "0")) {
                result.setResponseCode("1");
                result.setResponseMessage("Password updated successfully.");
            } else {
                result.setResponseCode("0");
                result.setResponseMessage("Password update failed.");
            }

        } catch (Exception ex) {
            // Log exception if logging exists
            result.setResponseCode("0");
            result.setResponseMessage("An error occurred while updating the password.");
        }

        return result;
    }
    private static String generateSalt() {
        int SALT_SIZE = 16;
        byte[] salt = new byte[SALT_SIZE];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }


//    public ApiResponse addUser(@RequestBody UserModel userModel) {
//        MembershipUserCustom membershipController = new MembershipUserCustom();
//        int numericUserId = 0;
//        ApiResponse response = new ApiResponse();
//        long entityCode = 0;
//        boolean isApproved = true;
//        MembershipCreateStatus status;
//
//        try {
//            // Verify that the provided EntityUserId is valid before proceeding
//            ApiResponse usrResponse = dataAccess.GetUserByEntityUserId(userModel.getEntityUserId());
//            if (Objects.equals(usrResponse.getStatusCode(), "1")) {
//
//                // Create the membership user
//                MembershipUserCustom user =new  MembershipUserCustom();
////                = membershipController.createUser(
////                        userModel.getUserName(),
////                        userModel.getPassword(),
////                        userModel.getEmail(),
////                        String.valueOf(userModel.getSecretQuestionId()),
////                        userModel.getSecretQuestionAnswer(),
////                        isApproved,
////                        java.util.UUID.randomUUID(),
////                        status
////                );
//
//                if (user != null) {
//                    String userId = user.getProviderUserKey();
//                    numericUserId = dataAccess.GetUserByUserId(userId).getUserID();
//                    entityCode = userModel.getEntityCode() == 0 ? 0 : userModel.getEntityCode();
//
//                    // Activate the user for the given entity
//                    int EntityUserId = dataAccess.ActivateUserbyEntity(numericUserId, userModel.getEntityUserId());
//
//                    // Retrieve role identifier for the entity
//                    String roleId = dataAccess.GetUserRole(EntityUserId);
//                    if (userId.length() > 0 && roleId != null && !roleId.isEmpty()) {
//                        // Assign the user to the role
//                        dataAccess.AddUsersInRoles(userId, roleId, numericUserId);
//                    }
//
//                    // Final check for successful creation
//                    if (EntityUserId > 0) {
//                        response.setStatus(true);
//                        response.setSuccessMessage("User added successfully.");
//                    } else {
//                        response.setStatus(false);
//                        response.setErrorMessage("User add unsuccessful.");
//                    }
//                } else {
//
//                    // Handle different MembershipCreateStatus cases
//                    switch (status) {
//                        case DUPLICATE_USER_NAME:
//                            response.setStatus(false);
//                            response.setErrorMessage("Username already exists. Please enter a different user name.");
//                            break;
//                        case DUPLICATE_EMAIL:
//                            response.setStatus(false);
//                            response.setErrorMessage("A username for that email address already exists. Please enter a different email address.");
//                            break;
//                        case INVALID_PASSWORD:
//                            response.setStatus(false);
//                            response.setErrorMessage("The password provided is invalid. Please enter a valid password value.");
//                            break;
//                        case INVALID_EMAIL:
//                            response.setStatus(false);
//                            response.setErrorMessage("The email address provided is invalid. Please check the value and try again.");
//                            break;
//                        case INVALID_ANSWER:
//                            response.setStatus(false);
//                            response.setErrorMessage("The password retrieval answer provided is invalid. Please check the value and try again.");
//                            break;
//                        case INVALID_QUESTION:
//                            response.setStatus(false);
//                            response.setErrorMessage("The password retrieval question provided is invalid. Please check the value and try again.");
//                            break;
//                        case INVALID_USER_NAME:
//                            response.setStatus(false);
//                            response.setErrorMessage("The user name provided is invalid. Please check the value and try again.");
//                            break;
//                        case PROVIDER_ERROR:
//                            response.setStatus(false);
//                            response.setErrorMessage("The authentication provider returned an error. Please verify your entry and try again. If the problem persists, please contact your system administrator.");
//                            break;
//                        case USER_REJECTED:
//                            response.setStatus(false);
//                            response.setErrorMessage("The user creation request has been canceled. Please verify your entry and try again. If the problem persists, please contact your system administrator.");
//                            break;
//                        default:
//                            response.setStatus(false);
//                            response.setErrorMessage("An unknown error occurred. Please verify your entry and try again. If the problem persists, please contact your system administrator.");
//                            break;
//                    }
//                }
//            } else {
//                // Propagate error from GetUserByEntityUserId
//                response.setStatus(false);
//                response.setErrorMessage(usrResponse.getMessage());
//            }
//
//            return response;
//        } catch (Exception e) {
//            // Log the exception and return a response
//            e.printStackTrace();
//            response.setStatus(false);
//            response.setErrorMessage("An unexpected error occurred.");
//            return response;
//        }
//    }

}
