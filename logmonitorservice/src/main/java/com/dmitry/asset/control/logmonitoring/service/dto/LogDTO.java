package com.dmitry.asset.control.logmonitoring.service.dto;

/**
 * Created by Dmitry Azarov 31.08.2017
 */
public class LogDTO {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogDTO logDTO = (LogDTO) o;

        if (infoCount != logDTO.infoCount) return false;
        if (debugCount != logDTO.debugCount) return false;
        if (errorCount != logDTO.errorCount) return false;
        return warningCount == logDTO.warningCount;
    }

    @Override
    public int hashCode() {
        int result = infoCount;
        result = 31 * result + debugCount;
        result = 31 * result + errorCount;
        result = 31 * result + warningCount;
        return result;
    }
}
