package com.techshard.batch.dao.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Voltage {

    @Id
    @Column (name = "segmentid", nullable = false)
    private Long segmentid;

    @NotNull
    @Column (name = "aeid", nullable = false)
    private Long aeid;

    @NotNull
    @Column (name = "segmenttype", nullable = false)
    private String segmenttype;

    @Column (name = "classification")
    private String classification;

    @Column (name = "description")
    private String description;


    public Voltage() {
    }

    public Voltage(Long segmentid, @NotNull Long aeid, @NotNull String segmenttype, String classification, String description) {
        this.segmentid = segmentid;
        this.aeid = aeid;
        this.segmenttype = segmenttype;
        this.classification = classification;
        this.description = description;
    }

    public Long getSegmentid() {
        return segmentid;
    }

    public Long getAeid() {
        return aeid;
    }

    public String getSegmenttype() {
        return segmenttype;
    }

    public String getClassification() {
        return classification;
    }

    public String getDescription() {
        return description;
    }

    public void setSegmentid(Long segmentid) {
        this.segmentid = segmentid;
    }

    public void setAeid(Long aeid) {
        this.aeid = aeid;
    }

    public void setSegmenttype(String segmenttype) {
        this.segmenttype = segmenttype;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
