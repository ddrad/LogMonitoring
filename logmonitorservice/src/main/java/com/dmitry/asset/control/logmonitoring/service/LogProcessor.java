package com.dmitry.asset.control.logmonitoring.service;

import com.dmitry.asset.control.logmonitoring.service.dto.LogDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

public class LogProcessor implements ItemProcessor<LogDTO, LogDTO> {

    private static final Logger LOGGER = Logger.getLogger(LogProcessor.class);

    @Override
    public LogDTO process(LogDTO item) throws Exception {
        LOGGER.info("Processing student information: "+ item);
        return item;
    }
}
