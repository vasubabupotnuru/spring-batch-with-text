package com.techshard.batch.configuration;

import com.techshard.batch.dao.entity.Voltage;

import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class VoltageProcessor implements ItemProcessor<Voltage, Voltage>{

    @Override
    public Voltage process(final Voltage voltage) {
        final Long segmentid = voltage.getSegmentid();
        final Long aeid = voltage.getAeid();
        final String segmenttype = voltage.getSegmenttype();
        final String classification = voltage.getClassification();
        final String description = voltage.getDescription();

        final Voltage processedVoltage = new Voltage();
        processedVoltage.setSegmentid(segmentid);
        processedVoltage.setAeid(aeid);
        processedVoltage.setSegmenttype(segmenttype);
        processedVoltage.setClassification(classification);
        processedVoltage.setDescription(description);


        return processedVoltage;
    }
}
