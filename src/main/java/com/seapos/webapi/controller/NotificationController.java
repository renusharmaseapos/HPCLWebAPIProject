package com.seapos.webapi.controller;
import com.seapos.webapi.models.SendNotification;
import com.seapos.webapi.models.ResponseBase;
import com.seapos.webapi.services.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("webapi/v1/Notification")
public class NotificationController {
    @Autowired
    private Notification service;

    @PostMapping("/SendNotification")
    public ResponseBase SendNotification(@RequestBody SendNotification notification) {
        return service.SendNotification(notification);
    }
}
