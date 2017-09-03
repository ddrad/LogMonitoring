package com.dmitry.asset.control.logmonitoring.service;

import com.dmitry.asset.control.logmonitoring.service.dto.LogDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

@Component
public class LogStore {

    private LogDTO logDTO;

    public LogDTO getLogDTO() {
        return logDTO;
    }

    public void setLogDTO(LogDTO logDTO) {
        this.logDTO = logDTO;
    }
}
