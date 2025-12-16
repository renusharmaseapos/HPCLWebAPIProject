package com.seapos.webapi.controller;

import com.seapos.webapi.Utility.JWTToken;
import com.seapos.webapi.models.*;
import com.seapos.webapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("oauth2")
public class AuthController {
    @Value("${spring.application.TokenExpiration}")
    private String TokenExpiration;
    @Value("${spring.application.RedirectURL}")
    private String RedirectUrl;

    UserService Uservic=new UserService();
    // private AuthenticationManager authenticationManager;
    @Autowired private JWTToken jwtUtil;
    // @Autowired private UserDetailsService userDetailsService;

    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String token ="";
        String ErrorMessage="";
        MembershipUserCustom userDetails;
        EntityUser user=null;
        userDetails= Uservic.GetUser(request.getUsername());
        if (userDetails == null) {
            ErrorMessage="The user name or password is incorrect.";
        }
        else if (userDetails.isIsLockedOut())
        {
            ErrorMessage="The user is locked.";
        }
        else if (!userDetails.isApproved())
        {
            ErrorMessage="Your account is disabled. Please contact IOCL support.";
        }
        else {
            user = Uservic.validateUserLogin(request.getUsername(), request.getPassword());
            if (Objects.equals(user.getMessage(), "")) {
                token = jwtUtil.generateToken(request.getUsername());
            } else {
                ErrorMessage = user.getMessage();
            }
        }
        if(Objects.equals(ErrorMessage, "")) {
            AuthResponse Arresponse =new AuthResponse();
            Arresponse.setAccess_token(token);
            if(Objects.equals(user.getRedirectURl(), ""))
                Arresponse.setIsRedirect(Boolean.FALSE);
            else {
                Arresponse.setRedirectUrl(RedirectUrl);
                Arresponse.setIsRedirect(Boolean.TRUE);
            }
            Arresponse.setExpires_in(JWTToken.expirytime/1000);
            Arresponse.setToken_type("Bearer");
            return ResponseEntity.ok(Arresponse);
        }
        else {
            AuthErrorResponse Aerresponse=new AuthErrorResponse();
            Aerresponse.setError("invalid_grant");
            Aerresponse.setError_description(ErrorMessage);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Aerresponse);
        }

    }
}





