package com.techshard.batch.configuration;

import com.techshard.batch.dao.entity.Voltage;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class VoltageFieldSetMapper implements FieldSetMapper<Voltage> {

    @Override
    public Voltage mapFieldSet(FieldSet fieldSet) {
        final Voltage voltage = new Voltage();

        voltage.setSegmentid(fieldSet.readLong("segmentid"));
        voltage.setAeid(fieldSet.readLong("aeid"));
        voltage.setSegmenttype(fieldSet.readString("segmenttype"));
        voltage.setClassification(fieldSet.readString("classification"));
        voltage.setDescription(fieldSet.readString("description"));
        return voltage;

    }
}
