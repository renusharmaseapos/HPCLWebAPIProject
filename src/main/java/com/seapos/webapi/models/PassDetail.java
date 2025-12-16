package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PassDetail {

        public String UserId ;
        public String Password ;
        public  int PasswordFormat ;
        public String PasswordSalt ;
        public LocalDateTime LastActivityDate ;
        public LocalDateTime LastLoginDate ;
        public int isPasswordvalid ;
        public Integer failedPasswordAttemptCount ;


}
