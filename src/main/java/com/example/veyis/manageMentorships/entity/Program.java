package com.example.veyis.manageMentorships.entity;

import com.example.veyis.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Program extends BaseEntity {
    @Column(name = "PROGRAMNAME", unique = true)
    private String program_name;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "STARTDATE")
    private Date start_date;
    @Column(name = "ENDDATE")
    private Date end_date;
}