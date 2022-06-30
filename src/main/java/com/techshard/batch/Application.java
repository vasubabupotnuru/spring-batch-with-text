package com.techshard.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    @Autowired
    JobLauncher jobLauncher;
    
    @Autowired
    Job importVoltageJob;

    @Value("${listenerlog}")
    String listenerlog;
    

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void perform() throws Exception {
        JobExecution jobExecution= null;
    	JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        
        jobExecution = jobLauncher.run(importVoltageJob, params);
		if(jobExecution!=null) {
			ExitStatus jobExistStatus = jobExecution.getExitStatus();
			if(ExitStatus.COMPLETED.equals(jobExistStatus)) {
				BufferedReader br = null;
				br = new BufferedReader(new FileReader(new File(listenerlog).getAbsoluteFile()));
				if(br.lines().count()==0) {
					LOGGER.info("No Errors in batch file");
					System.exit(0);
				}else {
					LOGGER.info("Errors found in batch file");
					System.exit(-1);
				}
			}
		}
		
		
    }

}
