package com.seapos.webapi.services;

import com.seapos.webapi.dataaccess.DeviceDataAccess;
import com.seapos.webapi.models.DeviceInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    DeviceDataAccess dataAccess;
    public List<DeviceInfo> GetDeviceList(long merchantCode) {
        // Placeholder for actual implementation
        dataAccess.GetDeviceList(merchantCode);
        return null;
    }

}
