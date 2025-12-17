package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MembershipUserCustom {
    private String ProviderUserKey;
    public String Email ;
    public String PasswordQuestion ;
    public String Comment ;
    public LocalDateTime CreateDate ;
    public LocalDateTime LastLoginDate ;
    public LocalDateTime LastActivityDate ;
    public LocalDateTime LastPasswordChangedDate ;
    public String UserId ;
    private boolean isApproved;
    private LocalDateTime lastLockoutDate;
    private boolean isOnline;
    private boolean IsLockedOut;
}
