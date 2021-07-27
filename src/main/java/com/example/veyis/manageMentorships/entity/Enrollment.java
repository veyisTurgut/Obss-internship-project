package com.example.veyis.manageMentorships.entity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Enrollment {
    @Id
    @Column(name = "PROGRAM_MENTEE_ID", unique = true)
    private Long program_mentee_id;
    @Column(name = "MENTEEUSERNAME", unique = true)
    private String menteeUsername;

}


