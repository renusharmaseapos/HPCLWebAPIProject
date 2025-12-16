package com.seapos.webapi.models;

import lombok.Getter;
import lombok.Setter;


public class DeviceInfo {

    private long DeviceNo;
    private long MerchantCode;
    private int DeviceTypeId;
    private  int Status;

    public long getDeviceNo() {
        return DeviceNo;
    }

    public void setDeviceNo(long deviceNo) {
        DeviceNo = deviceNo;
    }

    public long getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(long merchantCode) {
        MerchantCode = merchantCode;
    }

    public int getDeviceTypeId() {
        return DeviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        DeviceTypeId = deviceTypeId;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
