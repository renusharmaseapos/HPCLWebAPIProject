package com.seapos.webapi.controller;

import com.seapos.webapi.models.DeviceInfo;
import com.seapos.webapi.services.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("webapi/v1/device")
public class DeviceController {
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService service;

    @PostMapping("/GetDeviceList")
    public List<DeviceInfo> GetDeviceList(long merchantCode) {

        logger.info("API_HIT | GetDeviceList | merchantCode={}", merchantCode);
        return service.GetDeviceList(merchantCode);


    }
}
