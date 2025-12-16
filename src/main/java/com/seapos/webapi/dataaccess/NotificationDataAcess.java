package com.seapos.webapi.dataaccess;

import com.seapos.webapi.models.DeviceInfo;
import com.seapos.webapi.models.ResponseBase;
import com.seapos.webapi.models.SendNotification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationDataAcess {
    public ResponseBase SendNotification(SendNotification notification) {
        ResponseBase obj = new ResponseBase();
        try {
            Map<String, Object> inParams = new HashMap<>();
            inParams.put("p_Amount", notification.getAmount());
            inParams.put("p_TerminalId", notification.getTerminalId());
            inParams.put("p_OrderId", notification.getOrderId());
            inParams.put("p_Cashback", notification.getCashback());
            inParams.put("p_TxnDate", notification.getTxnDate());

            Map<String, Object> result = SQLHelper.executeScaler("UspAddNotificationData", "Seapos_Dev", inParams);
            if (result != null) {
                java.util.List rsList = (List) result.get("#result-set-1");
                Map mapData = (Map) rsList.get(0);
                obj.setResponseMessage((String) mapData.get("ResponseMessage"));
                if (obj.getResponseMessage().contains("Successfully")) {
                    obj.setResponseCode("200");
                } else {
                    obj.setResponseCode("400");
                }
            }
        }
        catch (Exception e) {
            obj.setResponseCode("500");
            obj.setResponseMessage(e.getMessage());
        }
        return obj;
    }
}
