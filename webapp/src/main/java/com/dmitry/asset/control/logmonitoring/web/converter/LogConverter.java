package com.dmitry.asset.control.logmonitoring.web.converter;

import com.dmitry.asset.control.logmonitoring.service.LogStore;
import com.dmitry.asset.control.logmonitoring.service.dto.LogDTO;
import com.dmitry.asset.control.logmonitoring.web.model.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

@Component
public class LogConverter {

    @Autowired
    private LogStore store;

    public LogModel toLogModel(LogDTO logDTO){
        LogModel model = new LogModel();
        model.setInfoCount(logDTO.getInfoCount());
        model.setDebugCount(logDTO.getDebugCount());
        model.setWarningCount(logDTO.getWarningCount());
        model.setErrorCount(logDTO.getErrorCount());
        return model;
    }

    public LogModel toLogModel(LogDTO logDTO, String time){
        LogModel model =  toLogModel(logDTO);
        model.setTime(time);
        return model;
    }
}
