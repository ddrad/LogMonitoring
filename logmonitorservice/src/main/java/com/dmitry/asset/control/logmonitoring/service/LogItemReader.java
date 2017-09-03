package com.dmitry.asset.control.logmonitoring.service;

import com.dmitry.asset.control.logmonitoring.service.dto.LogDTO;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dmitry Azarov on 26.08.2017.
 */

public class LogItemReader implements ItemReader<LogDTO> {

    private static final Logger LOGGER = Logger.getLogger(LogItemReader.class);
    private final String filename;
    private final int interval;
    private boolean flag = false;

    private ItemReader<LogDTO> delegate;

    LogItemReader(final String filename, int interval) {
        this.filename = filename;
        this.interval = interval;
    }

    @Override
    public LogDTO read() throws Exception {
       if(delegate == null && !flag) { // впервые
           delegate = new IteratorItemReader<>(logReader());
           flag = true;
       }
       else if (delegate != null && flag){ // второй раз
           flag = false;
       }
       else if (delegate != null && !flag){
           flag = true;
           delegate = new IteratorItemReader<>(logReader());
       }
       return delegate.read();
    }

    private List<LogDTO> logReader() throws IOException {
        LogDTO logDTO = new LogDTO();
        final Pattern patternLogLevel = Pattern.compile("(\\b[A-Z]+\\b)");
        final Pattern patternLogDate = Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}");

        try (ReversedLinesFileReader rlfr = new ReversedLinesFileReader(new File(filename), Charsets.UTF_8)) {
            String line;
            DateTime dt = null;

            while ((line = rlfr.readLine()) != null) {
                Matcher m = patternLogDate.matcher(line);
                while (m.find()) {
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                    dt = formatter.parseDateTime(m.group());
                }
                if (dt.isAfter(DateTime.now().minusMillis(interval))) {
                    m = patternLogLevel.matcher(line);
                    while (m.find()) {
                        String str = m.group();
                        switch (str) {
                            case "INFO":
                                logDTO.setInfoCount(logDTO.getInfoCount() + 1);
                                break;
                            case "DEBUG":
                                logDTO.setDebugCount(logDTO.getDebugCount() + 1);
                                break;
                            case "ERROR":
                                logDTO.setErrorCount(logDTO.getErrorCount() + 1);
                                break;
                            case "WARNING":
                                logDTO.setWarningCount(logDTO.getWarningCount() + 1);
                                break;
                            default:
                                System.out.println("match not found");
                        }
                    }
                }
            }
        }
        List<LogDTO> logDTOs = new ArrayList<LogDTO>();
        logDTOs.add(logDTO);
        return logDTOs;
    }
}
