package com.dmitry.asset.control.logmonitoring.web.controller;

import com.dmitry.asset.control.logmonitoring.service.dto.LogDTO;
import com.dmitry.asset.control.logmonitoring.web.converter.LogConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

@Controller
public class LogMonitoringController {

    @Autowired
    private LogConverter converter;

    @MessageMapping("/monitor")
    @SendTo("/topic/loginfo")
    public String send(LogDTO message) {
        return "This is websocket /topic/loginfo";
    }
}
