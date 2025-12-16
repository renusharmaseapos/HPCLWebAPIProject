package com.seapos.webapi.services;
import org.springframework.beans.factory.annotation.Value;
import com.seapos.webapi.dataaccess.UserDataAccess;
//import com.seapos.webapi.models.AppProperties;
import com.seapos.webapi.models.EntityUser;
import com.seapos.webapi.models.MembershipUserCustom;
import com.seapos.webapi.models.PassDetail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
//import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
@Service
public class UseService {

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


//    public static VerifyOtpModelOutput forgetUserPassword(ChangePasswordInput objPassword) {
//
//        VerifyOtpModelOutput result = new VerifyOtpModelOutput();
//
//        // Ensure password and confirm password are the same
//        if (!objPassword.getNewPassword().equals(objPassword.getConfirmPassword())) {
//            result.setResponseCode(0);
//            result.setResponseMessage("Password and Confirm Password do not match.");
//            return result;
//        }
//
//        // Get the user from membership system
//        MembershipUser user = MembershipService.getUser(objPassword.getUsername());
//
//        if (user == null) {
//            result.setResponseCode(0);
//            result.setResponseMessage("User not found.");
//            return result;
//        }
//
//        // Check if user is locked
//        if (user.isLockedOut()) {
//            result.setResponseCode(0);
//            result.setResponseMessage("User account is locked. Kindly unlock the user.");
//            return result;
//        }
//
//        try {
//            // Reset password
//            String resetPassword = MembershipService.resetPassword(user);
//
//            // Change password
//            boolean isChanged = MembershipService.changePassword(
//                    user,
//                    resetPassword,
//                    objPassword.getNewPassword()
//            );
//
//            if (isChanged) {
//                result.setResponseCode(1);
//                result.setResponseMessage("Password updated successfully.");
//            } else {
//                result.setResponseCode(0);
//                result.setResponseMessage("Password update failed.");
//            }
//
//        } catch (Exception ex) {
//            // Log exception if logging exists
//            result.setResponseCode(0);
//            result.setResponseMessage("An error occurred while updating the password.");
//        }
//
//        return result;
//    }

}
