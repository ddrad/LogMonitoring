package com.dmitry.asset.control.logmonitoring.service;

import com.dmitry.asset.control.logmonitoring.service.dto.LogDTO;
import com.dmitry.asset.control.logmonitoring.service.dto.LogOutputMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

@Component
public class LogJobLauncher {

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private LogStore store;

    private static final Logger LOGGER = Logger.getLogger(LogJobLauncher.class);

    private final Job job;

    private final JobLauncher jobLauncher;

    @Autowired
    LogJobLauncher(@Qualifier("logJob") Job job, @Qualifier("jobLauncher") JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(fixedRateString = "${log.file.interval}")
    void launchCsvFileToDatabaseJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JsonProcessingException {
        LOGGER.info("Starting log monitoring job");

        jobLauncher.run(job, newExecution());

        LOGGER.info("Stopping log monitoring job");
        LOGGER.info("Send message to websocket");
        template.convertAndSend("/topic/loginfo", getLogOutputMessag());
    }

    private JobParameters newExecution() {
        Map<String, JobParameter> parameters = new HashMap<>();

        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);

        return new JobParameters(parameters);
    }

    private String getLogOutputMessag() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(
            new LogOutputMessage(store.getLogDTO(),
                    new SimpleDateFormat("HH:mm").format(new Date())));

    }
}
