package com.seapos.webapi.dataaccess;

import com.seapos.webapi.models.*;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

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

        Map<String, Object> result = SQLHelper.getRecord("aspnet_Membership_GetUserByName", inParams);
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

        Map<String, Object> result = SQLHelper.getRecord("uspGetEntityUser", inParams);
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
        Map<String, Object> result = SQLHelper.getRecord("aspnet_Membership_GetPasswordWithFormat", inParams);
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
        inParams.put("p_ApplicationName", Appname);
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
        Map<String, Object> result = SQLHelper.getRecord("aspnet_Membership_UnlockUser", inParams);
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
        Map<String, Object> result = SQLHelper.getRecord("aspnet_Membership_GetPasswordWithFormat", inParams);
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
        Map<String, Object> result = SQLHelper.getRecord("aspnet_GetUserByUserId", inParams);
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

    public String ChangePassword(String UserName, String NewPassword, String PasswordSalt, String PasswordFormat) {

        String Result = "";
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_ApplicationName", Appname);
        inParams.put("p_UserName", UserName);
        inParams.put("p_NewPassword", NewPassword);
        inParams.put("p_PasswordSalt", PasswordSalt);
        inParams.put("p_CurrentTimeUtc", LocalDateTime.now());
        inParams.put("p_PasswordFormat", PasswordFormat);
        Map<String, Object> result = SQLHelper.executeScaler("aspnet_Membership_SetPassword", inParams);
        if (result != null) {
            java.util.List rsList = (List) result.get("#result-set-1");
            Map mapData = (Map) rsList.get(0);
            Result = (String) mapData.get("Result");
        }

        return Result;
    }


    public int ActivateUserbyEntity(int NumericUserId, int EntityUserId) {

        int output = 0;
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("EntityUserId", EntityUserId);
        inParams.put("NumericUserId", NumericUserId);
        Map<String, Object> result = SQLHelper.executeScaler("uspActivateUser", inParams);
        if (result != null) {
            java.util.List rsList = (List) result.get("#result-set-1");
            Map mapData = (Map) rsList.get(0);
            output = (int) mapData.get("EntityUserId");
        }

        return output;
    }

    public String GetUserRole(int EntityUserId) {

        String output = "";
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("EntityUserId", EntityUserId);

        Map<String, Object> result = SQLHelper.executeScaler("uspGetUserRole", inParams);
        if (result != null) {
            java.util.List rsList = (List) result.get("#result-set-1");
            Map mapData = (Map) rsList.get(0);
            output = (String) mapData.get("RoleId");
        }

        return output;
    }

    public String addOrUpdateUser(@Nonnull AddUserRequest r) {

        try {
            logger.info("DAL | Calling uspAddEntityUser | email={}", r.getEmail());

            return jdbcTemplate.queryForObject(
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

        } catch (DataAccessException ex) {

            String dbMsg = ex.getMostSpecificCause() != null
                    ? ex.getMostSpecificCause().getMessage()
                    : ex.getMessage();

            logger.error("DAL ERROR | uspAddEntityUser | email={} | msg={}",
                    r.getEmail(), dbMsg);


            throw ex;
        }
    }

    public List<GetUserInfo> getUserInfoList(@Nonnull GetUserInfoRequest request) {

        String sql = "CALL uspGetUserInfoList(?,?,?,?,?)";
        logger.info(
                "Calling uspGetUserInfoList | userTypeId={} | userName={}",
                request.getUserTypeId(),
                request.getUserName()
        );
        int pageSize = 0;
        int skipCount = 0;
        try {
            return jdbcTemplate.query(
                    sql,
                    new Object[]{
                            0, // p_UserId (not used now)
                            request.getUserTypeId() == 0 ? null : request.getUserTypeId(),
                            request.getUserName(),
                            skipCount,
                            pageSize
                    },
                    new GetUserInfoRowMapper()
            );
        } catch (Exception ex) {
            logger.error(
                    "DB_ERROR | uspGetUserInfoList | userTypeId={} | userName={}",
                    request.getUserTypeId(),
                    request.getUserName(),
                    ex
            );
            throw ex; // let global handler deal with it
        }
    }

    public List<GetUserInfo> getUserInfoEditList(@Nonnull UserInfoSearch request) {

        String sql = "CALL uspGetUserInfoList(?,?,?,?,?)";
        logger.info(
                "Calling uspGetUserInfoList | UserId={}",
                request.getUserId()

        );
//        int pageSize =0;
//        int skipCount =0;
        try {
            return jdbcTemplate.query(
                    sql,
                    new Object[]{
                            request.getUserId(),// p_UserId
                            null,                // p_UserTypeId
                            null,                // p_UserName
                            0,                   // p_SkipCount
                            0                    // p_PageSize
                    },
                    new GetUserInfoRowMapper()
            );
        } catch (Exception ex) {
            logger.error(
                    "DB_ERROR | uspGetUserInfoList | UserId={}",
                    request.getUserId(),
                    ex
            );
            throw ex; // let global handler deal with it
        }
    }

    private static class GetUserInfoRowMapper implements RowMapper<GetUserInfo> {

        @Nonnull
        @Override
        public GetUserInfo mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {

            GetUserInfo u = new GetUserInfo();
            u.setEntityUserId(rs.getInt("UserId"));
            u.setFirstName(rs.getString("FirstName"));
            u.setLastName(rs.getString("LastName"));
            u.setUsername(rs.getString("Username"));
            u.setEmail(rs.getString("Email"));
            u.setUserType(rs.getString("UserType"));
            u.setUserStatus(rs.getString("UserStatus"));
            u.setMobileNo(rs.getString("Mobile"));
            u.setEntityTypeId(rs.getInt("EntityTypeId"));
            u.setRoleId(rs.getString("RoleId"));
            u.setIsLockedOut(rs.getInt("IsLockedOut"));
            return u;
        }
    }
}
//    public static MembershipUserCustom CreateUser(String username, String password, String email, String passwordQuestion, String encodedPasswordAnswer, String salt,
//                                                  MembershipCreateStatus status, int UniqueEmail, long empCode , int usertype )
//    {
//        String output = "";
//        MembershipUserCustom objuser = new MembershipUserCustom();
//        try
//        {
//            Map<String, Object> inParams = new HashMap<>();
//            inParams.put("p_ApplicationName", Appname);
//            inParams.put("p_UserName", username);
//            inParams.put("p_Password", password);
//            inParams.put("p_PasswordSalt", email);
//            inParams.put("p_Email", email);
//            inParams.put("p_PasswordQuestion", passwordQuestion);
//            inParams.put("p_PasswordAnswer", encodedPasswordAnswer);
//            inParams.put("p_IsApproved", true);
//            inParams.put("p_CurrentTimeUtc", LocalDateTime.now());
//            inParams.put("p_CreateDate",  LocalDateTime.now());
//            inParams.put("p_UniqueEmail", UniqueEmail);
//
//            inParams.put("p_PasswordFormat", 1);
//            inParams.put("p_UserId", EntityUserId);
//
//            try
//            {
//                Map<String, Object> result = SQLHelper.executeScaler("aspnet_Membership_CreateUser", inParams);
//                if (result != null) {
//                    java.util.List rsList = (List) result.get("#result-set-1");
//                    Map mapData = (Map) rsList.get(0);
//                    output = (String) mapData.get("ErrorCode");
//                }
//            }
//            catch (SqlException sqlEx)
//            {
//                if (sqlEx.Number == 2627 || sqlEx.Number == 2601 || sqlEx.Number == 2512)
//                {
//                    status = MembershipCreateStatus.DuplicateUserName;
//                    return null;
//                }
//                throw;
//            }
//            int iStatus = ((Return.Value != null) ? ((int)Return.Value) : -1);
//            if (iStatus < 0 || iStatus > (int)MembershipCreateStatus.ProviderError)
//                iStatus = (int)MembershipCreateStatus.ProviderError;
//            status = (MembershipCreateStatus)iStatus;
//            if (iStatus != 0) // !success
//                return null;
//
//            string ProviderUserKey = (new Guid(Key.Value.ToString())).ToString();
//            dt = dt.ToLocalTime();
//
//
//            objuser.UserName = username;
//            objuser.ProviderUserKey = ProviderUserKey;
//            objuser.Email = email;
//            objuser.PasswordQuestion = passwordQuestion;
//            objuser.IsApproved = true;
//            objuser.IsLockedOut = false;
//            objuser.CreationDate = dt;
//            objuser.LastLoginDate = dt;
//            objuser.LastPasswordChangedDate = dt;
//        }
//        finally
//        {
//        }
//        return objuser;
//
//    }
//    private static LocalDateTime  RoundToSeconds(LocalDateTime  utcDateTime)
//    {
//        return new LocalDateTime(utcDateTime.getYear(), utcDateTime.getMonth(), utcDateTime.getDayOfMonth(), utcDateTime.getHour(), utcDateTime.getMinute(), utcDateTime.getSecond(), Instant.now());
//    }