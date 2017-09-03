package com.dmitry.asset.control.logmonitoring.web.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

@Configuration
@EnableScheduling
@ComponentScan({"com.dmitry.asset.control.logmonitoring.service"})
@PropertySource(value = {"classpath:application.properties"})
public class ScheduleConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
