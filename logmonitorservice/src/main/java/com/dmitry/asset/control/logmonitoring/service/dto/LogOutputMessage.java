package com.dmitry.asset.control.logmonitoring.service.dto;

/**
 * Created by user on 31.08.2017.
 */

public class LogOutputMessage {

    private LogDTO log;
    private String time;

    public LogOutputMessage(final LogDTO log, final String time) {

        this.log = log;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public LogDTO getLog() {
        return log;
    }
}