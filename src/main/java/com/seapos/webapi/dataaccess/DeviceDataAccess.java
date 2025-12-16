package com.seapos.webapi.dataaccess;

import com.seapos.webapi.models.DeviceInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceDataAccess {

    public List<DeviceInfo> GetDeviceList(long merchantCode) {
        List<DeviceInfo> devices= new ArrayList<>();
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("@MerchantCode", merchantCode);

        List<Map<String, Object>> result = SQLHelper.getRecords("uspGetDeviceList","HPCL", inParams);
        if(result != null)
        {
            for(Map<String, Object> row : result)
            {
                DeviceInfo device = new DeviceInfo();
                device.setDeviceNo (Long.parseLong((String)row.get("DeviceNo")));
                device.setMerchantCode (Long.parseLong((String)row.get("MerchantCode")));
                device.setDeviceTypeId (Integer.parseInt((String)row.get("DeviceTypeId")));
                device.setStatus (Integer.parseInt((String)row.get("Status")));
                devices.add(device);
            }
        }
        return devices;
    }


}
