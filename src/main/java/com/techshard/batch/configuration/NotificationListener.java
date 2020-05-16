package com.techshard.batch.configuration;

import com.techshard.batch.dao.entity.Voltage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener extends JobExecutionListenerSupport{

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NotificationListener(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(final JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("!!! JOB FINISHED! Time to verify the results");

            jdbcTemplate.query("SELECT segmentid, aeid, segmenttype, classification, description FROM voltage",
                    (rs, row) -> new Voltage(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5))
            ).forEach(voltage -> LOGGER.info("Found <" + voltage + "> in the database."));
        }
    }
}
