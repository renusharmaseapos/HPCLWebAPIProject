package com.seapos.webapi.services;
import com.seapos.webapi.dataaccess.NotificationDataAcess;
import com.seapos.webapi.models.ResponseBase;
import com.seapos.webapi.models.SendNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Notification {

    @Autowired
    NotificationDataAcess dataAccess;

    public ResponseBase SendNotification(SendNotification notification) {
        ResponseBase obj ;
        if(notification.getOrderId()!=null && notification.getOrderId().length()>15){
            obj = new ResponseBase();
            obj.setResponseCode("400");
            obj.setResponseMessage("OrderId should be less than or equal to 15 digit");
            return obj;
        }
        else if(notification.getAmount()==null ){
            obj = new ResponseBase();
            obj.setResponseCode("400");
            obj.setResponseMessage("Amount is required");
            return obj;
        }
        else if(notification.getCashback() ==0){
            obj = new ResponseBase();
            obj.setResponseCode("400");
            obj.setResponseMessage("Cashback is required");
            return obj;
        }else if(notification.getTxnDate()==null ){
            obj = new ResponseBase();
            obj.setResponseCode("400");
            obj.setResponseMessage("TxnDate is required");
            return obj;
        }
        else
          return  dataAccess.SendNotification(notification);
    }
}
