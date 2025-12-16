package com.seapos.webapi.dataaccess;
import com.seapos.webapi.Filter.JwtRequestFilter;
import com.seapos.webapi.exception.DalException;
import com.seapos.webapi.exception.ValidationException;
import com.seapos.webapi.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.seapos.webapi.Filter.JwtRequestFilter.Appname;

@Service
public class UserDataAccess {

    private static final Logger logger = LoggerFactory.getLogger(UserDataAccess.class);
    private final JdbcTemplate jdbcTemplate;

    public UserDataAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public MembershipUserCustom GetUser(String UserName) {
        MembershipUserCustom Userdata = new MembershipUserCustom();
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_UserName", UserName);

        Map<String, Object> result = SQLHelper.getRecord("aspnet_Membership_GetUserByName", "Seapos_Dev", inParams);
        if (!result.isEmpty()) {
            List rsList = (List) result.get("#result-set-1");
            if (!rsList.isEmpty()) {
                Map mapData = (Map) rsList.get(0);
                Userdata.setEmail((String) mapData.get("Email"));
                Userdata.setPasswordQuestion((String) mapData.get("PasswordQuestion"));
                Userdata.setComment((String) mapData.get("Comment"));
                Userdata.setApproved((boolean) mapData.get("IsApproved"));
                Userdata.setCreateDate((LocalDateTime) mapData.get("CreateDate"));
                Userdata.setLastLoginDate((LocalDateTime) mapData.get("LastLoginDate"));
                Userdata.setLastActivityDate((LocalDateTime) mapData.get("LastActivityDate"));
                Userdata.setLastPasswordChangedDate((LocalDateTime) mapData.get("LastPasswordChangedDate"));
                Userdata.setUserId((String) mapData.get("UserId"));
                Userdata.setLastLockoutDate((LocalDateTime) mapData.get("LastLockoutDate"));
                Userdata.setIsLockedOut((boolean) mapData.get("IsLockedOut"));
            } else
                Userdata = null;
        }
        return Userdata;
    }

    public EntityUser GetUserbyName(String UserName) {
        EntityUser Userdata = new EntityUser();
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_UserName", UserName);

        Map<String, Object> result = SQLHelper.getRecord("uspGetEntityUser", "Seapos_Dev", inParams);
        if (!result.isEmpty()) {
            List rsList = (List) result.get("#result-set-1");
            if (!rsList.isEmpty()) {
                Map mapData = (Map) rsList.get(0);
                Userdata.setEntityTypeId((long) mapData.get("EntityTypeId"));
                Userdata.setEntityId((long) mapData.get("EntityId"));
                Userdata.setUserId((int) mapData.get("UserId"));
                Userdata.setActive((long) mapData.get("Active"));
            } else
                Userdata = null;

        }
        return Userdata;
    }

    public PassDetail GetPassworddetails(String UserName) {

        PassDetail passworddetail = new PassDetail();
        EntityUser Userdata = new EntityUser();
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_UserName", UserName);
        Map<String, Object> result = SQLHelper.getRecord("aspnet_Membership_GetPasswordWithFormat", "Seapos_Dev", inParams);
        if (!result.isEmpty()) {
            List rsList = (List) result.get("#result-set-1");
            if (!rsList.isEmpty()) {
                Map mapData = (Map) rsList.get(0);
                passworddetail.setUserId((String) mapData.get("UserId"));
                passworddetail.setPassword((String) mapData.get("Password"));
                passworddetail.setPasswordFormat((int) mapData.get("PasswordFormat"));
                passworddetail.setPasswordSalt((String) mapData.get("PasswordSalt"));
                passworddetail.setLastLoginDate((LocalDateTime) mapData.get("LastLoginDate"));
                passworddetail.setLastActivityDate((LocalDateTime) mapData.get("LastActivityDate"));
                passworddetail.setFailedPasswordAttemptCount((Integer) mapData.get("FailedPasswordAttemptCount"));
            } else
                passworddetail = null;
        }
        return passworddetail;

    }

    public int UpdateUserInfo(String username, boolean isPasswordCorrect, boolean updateLastLoginActivityDate, int MaxInvalidPasswordAttempts,
                              LocalDateTime lastLoginDate, LocalDateTime lastActivityDate) {

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_ApplicationName", "HPCL");
        inParams.put("p_UserName", username);
        inParams.put("p_IsPasswordCorrect", isPasswordCorrect);
        inParams.put("p_UpdateLastLoginActivityDate", updateLastLoginActivityDate);
        inParams.put("p_MaxInvalidPasswordAttempts", MaxInvalidPasswordAttempts);
        inParams.put("p_PasswordAttemptWindow", 1440);
        inParams.put("p_CurrentTimeUtc", LocalDateTime.now());
        inParams.put("p_LastLoginDate", isPasswordCorrect ? LocalDateTime.now() : lastLoginDate);
        inParams.put("p_LastActivityDate", isPasswordCorrect ? LocalDateTime.now() : lastActivityDate);
        Map<String, Object> result = SQLHelper.executeNonQuery("aspnet_Membership_UpdateUserInfo", inParams);
        return 0;
    }

    public int AddLoginUserTrailDetails(String username, String ProviderUserKey,
                                        String UserIp, String SessionID,
                                        boolean IsLoggedIn, LocalDateTime LastLoginDate) {

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_UserId", ProviderUserKey);
        inParams.put("p_UserName", username);
        inParams.put("p_LastLoginDate", LastLoginDate);
        inParams.put("p_UserIp", UserIp);
        inParams.put("p_SessionID", SessionID);
        inParams.put("p_IsLoggedIn", IsLoggedIn);
        Map<String, Object> result = SQLHelper.executeNonQuery("uspINSUsersTrailInfo", inParams);
        return 0;
    }

    public boolean UnlockUser(String Usernamee) {

        Map<String, Object> inParams = new HashMap<>();
        Long resultCode = 0L;
        inParams.put("p_UserName", Usernamee);
        Map<String, Object> result = SQLHelper.getRecord("aspnet_Membership_UnlockUser", "Seapos_Dev", inParams);
        if (!result.isEmpty()) {
            List rsList = (List) result.get("#result-set-1");
            Map mapData = (Map) rsList.get(0);
            resultCode = (Long) mapData.get("code");

        }
        return Objects.equals(resultCode, 1);

    }

    public ApiResponse GetUserByEntityUserId(int entityUserId) {

        ApiResponse objRes = new ApiResponse();
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_EntityUserId", entityUserId);
        Map<String, Object> result = SQLHelper.getRecord("aspnet_Membership_GetPasswordWithFormat", "Seapos_Dev", inParams);
        if (!result.isEmpty()) {
            List rsList = (List) result.get("#result-set-1");
            if (!rsList.isEmpty()) {
                Map mapData = (Map) rsList.get(0);
                objRes.setStatusCode((String) mapData.get("ResponseCode"));
                objRes.setMessage((String) mapData.get("ResponseMessage"));
            } else
                objRes = null;
        }
        return objRes;

    }

    public UserInfo GetUserByUserId(String userId) {

        UserInfo objRes = new UserInfo();
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_UserID", userId);
        Map<String, Object> result = SQLHelper.getRecord("aspnet_GetUserByUserId", "Seapos_Dev", inParams);
        if (!result.isEmpty()) {
            List rsList = (List) result.get("#result-set-1");
            if (!rsList.isEmpty()) {
                Map mapData = (Map) rsList.get(0);
                objRes.setProviderUserKey((String) mapData.get("UserId"));
                objRes.setUserName((String) mapData.get("UserName"));
                objRes.setNumericUserId((Integer) mapData.get("NumericUserId"));
            } else
                objRes = null;
        }
        return objRes;

    }

    public int AddUsersInRoles(String userID, String roleId, int numericUserId) {
        UUID outUserGuid = null;
        UUID outRoleGuid = null;
        try {
            outUserGuid = UUID.fromString(userID);
            outRoleGuid = UUID.fromString(roleId);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_userID", outUserGuid);
        inParams.put("p_roleId", outRoleGuid);
        inParams.put("p_locationId", 0);
        inParams.put("p_createdBy", numericUserId);
        inParams.put("p_active", true);
        Map<String, Object> result = SQLHelper.executeNonQuery("uspInsertUsersInRoles", inParams);
        return 0;
    }
    public String ChangePassword(String UserName, String NewPassword,String PasswordSalt,String PasswordFormat) {

            String Result = "";
                Map<String, Object> inParams = new HashMap<>();
                inParams.put("p_ApplicationName", Appname);
                inParams.put("p_UserName", UserName);
                inParams.put("p_NewPassword", NewPassword);
                inParams.put("p_PasswordSalt", PasswordSalt);
                inParams.put("p_CurrentTimeUtc", LocalDateTime.now());
                inParams.put("p_PasswordFormat", PasswordFormat);
                Map<String, Object> result = SQLHelper.executeScaler("aspnet_Membership_SetPassword", "Seapos_Dev", inParams);
                if (result != null) {
                    java.util.List rsList = (List) result.get("#result-set-1");
                    Map mapData = (Map) rsList.get(0);
                    Result = (String) mapData.get("Result");
                }

            return Result;
        }

    public String addOrUpdateUser(AddUserRequest r) {

        try {
            logger.info("DAL | uspAddEntityUser | email={}", r.getEmail());

            String dbResponse = jdbcTemplate.queryForObject(
                    "CALL uspAddEntityUser(?,?,?,?,?,?,?,?,?,?,?)",
                    String.class,
                    r.getFirstName(),
                    r.getLastName(),
                    r.getEmail(),
                    r.getMobile(),
                    r.getEntityTypeId(),
                    r.getEntityId(),
                    r.getRoleId(),
                    r.getUserId(),
                    r.getClientIds(),
                    r.getUserStatus(),
                    r.getCreatedBy()
            );

            // DB SUCCESS
            if (dbResponse == null || dbResponse.trim().isEmpty()) {
                throw new DalException("Empty response from database", null);
            }

            return dbResponse;

        } catch (DataAccessException dae) {

            String dbMsg = dae.getMostSpecificCause().getMessage();
            logger.error("DAL ERROR | email={} | msg={}", r.getEmail(), dbMsg);

            // BUSINESS VALIDATION ERRORS
            if (dbMsg.contains("Duplicate Email")) {
                throw new ValidationException("Email already exists");
            }

            if (dbMsg.contains("Invalid Merchant")) {
                throw new ValidationException("Invalid Merchant Id");
            }

            // SYSTEM / DB FAILURE
            throw new DalException("Database operation failed", dae);
        }
    }
}