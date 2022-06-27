package com.techshard.batch.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.annotation.OnWriteError;

import com.techshard.batch.dao.entity.Employee;
import com.techshard.batch.dao.entity.Voltage;

public class StepSkipListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepSkipListener.class);

	@OnReadError
	public void onSkipInRead(Exception t) {
		LOGGER.error("Exception while reading with message {}",t.getMessage());
	}

	@OnWriteError
	public void onSkipInWrite(Exception t, List<Object> l) {
		LOGGER.error("Exception while saving with message {} ",t.getMessage());
	}

	@OnProcessError
	public void onSkipInProcess(Object item, Exception t) {
		LOGGER.error("Exception while processing a record {} with message {}",item,t.getMessage());
	}
}