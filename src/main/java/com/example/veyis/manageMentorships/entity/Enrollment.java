package com.example.veyis.manageMentorships.entity;
import com.example.veyis.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Enrollment extends BaseEntity {

    @Column(name = "PROGRAM_NAME")
    private Long program_id;
    @Column(name = "MENTEE_USERNAME")
    private Long mentee_id;
    @Column(name = "IS_ACTIVE")
    private boolean is_active;
    @Column(name = "MENTORCOMMENT")
    private String mentor_comment;
    @Column(name = "MENTEECOMMENT")
    private String mentee_comment;

}


