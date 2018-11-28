/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelivery_cl;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * RedeliveryCondition generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`redelivery_condition`")
public class RedeliveryCondition implements Serializable {

    private Integer id;
    private Integer acId;
    private String component;
    private String redelFlightHours;
    private String redelFlightCycle;
    private String remarks;
    private Aircraft aircraft;

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "\"redelivery_condition_ID_seq\"" , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @Column(name = "`ID`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`ac_id`", nullable = true, scale = 0, precision = 10)
    public Integer getAcId() {
        return this.acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    @Column(name = "`component`", nullable = true, length = 255)
    public String getComponent() {
        return this.component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    @Column(name = "`redel_flight_hours`", nullable = true, length = 255)
    public String getRedelFlightHours() {
        return this.redelFlightHours;
    }

    public void setRedelFlightHours(String redelFlightHours) {
        this.redelFlightHours = redelFlightHours;
    }

    @Column(name = "`redel_flight_cycle`", nullable = true, length = 255)
    public String getRedelFlightCycle() {
        return this.redelFlightCycle;
    }

    public void setRedelFlightCycle(String redelFlightCycle) {
        this.redelFlightCycle = redelFlightCycle;
    }

    @Column(name = "`remarks`", nullable = true, length = 255)
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`ac_id`", referencedColumnName = "`ID`", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "`FK_redelivery_condition_HyX4t`"))
    @Fetch(FetchMode.JOIN)
    public Aircraft getAircraft() {
        return this.aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        if(aircraft != null) {
            this.acId = aircraft.getId();
        }

        this.aircraft = aircraft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RedeliveryCondition)) return false;
        final RedeliveryCondition redeliveryCondition = (RedeliveryCondition) o;
        return Objects.equals(getId(), redeliveryCondition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}