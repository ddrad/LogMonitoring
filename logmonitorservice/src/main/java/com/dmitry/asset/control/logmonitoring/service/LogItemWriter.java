package com.dmitry.asset.control.logmonitoring.service;

import com.dmitry.asset.control.logmonitoring.service.dto.LogDTO;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

public class LogItemWriter implements ItemWriter<LogDTO> {

    @Autowired
    private LogStore store;
    private static final Logger LOGGER = Logger.getLogger(LogItemWriter.class);

    @Override
    public void write(List<? extends LogDTO> items) throws Exception {
        LOGGER.info("Received the information of "+ items.size()+" logfile");
        items.forEach(l -> {LOGGER.debug("Received the information of a logfile: "+ l);
            store.setLogDTO(l);});
    }
}