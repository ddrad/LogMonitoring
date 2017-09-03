package com.dmitry.asset.control.logmonitoring.service;

import com.dmitry.asset.control.logmonitoring.service.dto.LogDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

@Configuration
public class LogJobConfig {
    @Value("${log.file.path}")
    private String source;
    @Value("${log.file.interval}")
    private int interval;

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository getJobRepository() throws Exception {
        MapJobRepositoryFactoryBean repository = new MapJobRepositoryFactoryBean(getTransactionManager());
        return repository.getObject();
    }


    @Bean(name = "jobLauncher")
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        return jobLauncher;
    }

    @Bean
    public ItemReader<LogDTO> logItemReader(Environment environment) {
       return new LogItemReader(source, interval);
    }


    @Bean
    ItemProcessor<LogDTO, LogDTO> logItemProcessor() {
        return new LogProcessor();
    }

    @Bean
    ItemWriter<LogDTO> logItemWriter() {
        return new LogItemWriter();
    }

    @Bean
    StepBuilderFactory stepBuilderFactory() throws Exception {
        return new StepBuilderFactory(getJobRepository(), getTransactionManager());
    }

    @Bean(name = "logStep")
    Step logStep(ItemReader<LogDTO> logItemReader,
                 ItemProcessor<LogDTO, LogDTO> logItemProcessor,
                 ItemWriter<LogDTO> logItemWriter,
                 StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("logStep")
                .<LogDTO, LogDTO>chunk(1)
                .reader(logItemReader)
                .processor(logItemProcessor)
                .writer(logItemWriter)
                .build();
    }

    @Bean
    JobBuilderFactory jobBuilderFactory() throws Exception {
        return new JobBuilderFactory(getJobRepository());
    }

    @Bean(name = "logJob")
    Job logJob(JobBuilderFactory jobBuilderFactory,
               @Qualifier("logStep") Step csvStudentStep) {
        return jobBuilderFactory.get("logJob")
                .incrementer(new RunIdIncrementer())
                .flow(csvStudentStep)
                .end()
                .build();
    }
}