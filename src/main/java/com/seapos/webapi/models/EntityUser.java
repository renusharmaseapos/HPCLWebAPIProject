package com.seapos.webapi.models;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EntityUser {
    public long EntityTypeId ;
    public long EntityId ;
    public int UserId ;
    public long Active ;
    public String Message ;
    public String RedirectURl ;
}
