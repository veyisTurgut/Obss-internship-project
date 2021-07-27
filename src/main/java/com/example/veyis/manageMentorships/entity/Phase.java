package com.example.veyis.manageMentorships.entity;

import com.example.veyis.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Phase extends BaseEntity {

    @Column(name = "PHASENAME", unique = true)
    private String phase_name;
    @Column(name = "STARTDATE")
    private Date start_date;
    @Column(name = "ENDDATE")
    private Date end_date;
    @Column(name = "POINT")
    private int point;
    @Column(name = "EXPERIENCE")
    private String experience;
    @Column(name = "IS_ACTIVE")
    private boolean is_active;
    @Column(name = "PROGRAM_ID")
    private Long program_id;

}