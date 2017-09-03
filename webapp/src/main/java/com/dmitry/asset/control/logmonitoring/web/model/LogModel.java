package com.dmitry.asset.control.logmonitoring.web.model;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

public class LogModel {
    private String time;
    private int infoCount;
    private int debugCount;
    private int errorCount;
    private int warningCount;

    public int getInfoCount() {
        return infoCount;
    }

    public void setInfoCount(int infoCount) {
        this.infoCount = infoCount;
    }

    public int getDebugCount() {
        return debugCount;
    }

    public void setDebugCount(int debugCount) {
        this.debugCount = debugCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "time=" + time +
                ", infoCount=" + infoCount +
                ", debugCount=" + debugCount +
                ", errorCount=" + errorCount +
                ", warningCount=" + warningCount;
    }
}
