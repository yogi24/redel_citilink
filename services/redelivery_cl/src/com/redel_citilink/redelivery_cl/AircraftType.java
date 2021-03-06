/*Copyright (c) 2017-2018 asyst.co.id All Rights Reserved.
 This software is the confidential and proprietary information of asyst.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with asyst.co.id*/
package com.redel_citilink.redelivery_cl;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * AircraftType generated by WaveMaker Studio.
 */
@Entity
@Table(name = "`aircraft_type`")
public class AircraftType implements Serializable {

    private Integer id;
    private String type;
    private String manufactur;
    private List<Aircraft> aircrafts;

    @Id
    @SequenceGenerator(name = "generator", sequenceName = "\"aircraft_type_ID_seq\"" , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @Column(name = "`ID`", nullable = false, scale = 0, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "`type`", nullable = true, length = 255)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "`manufactur`", nullable = true, length = 255)
    public String getManufactur() {
        return this.manufactur;
    }

    public void setManufactur(String manufactur) {
        this.manufactur = manufactur;
    }

    @JsonInclude(Include.NON_EMPTY)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aircraftType")
    @Cascade({CascadeType.SAVE_UPDATE})
    public List<Aircraft> getAircrafts() {
        return this.aircrafts;
    }

    public void setAircrafts(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

    @PostPersist
    public void onPostPersist() {
        if(aircrafts != null) {
            aircrafts.forEach(_aircraft -> _aircraft.setAircraftType(this));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AircraftType)) return false;
        final AircraftType aircraftType = (AircraftType) o;
        return Objects.equals(getId(), aircraftType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}