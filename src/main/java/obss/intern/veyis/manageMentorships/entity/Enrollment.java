package obss.intern.veyis.manageMentorships.entity;
import obss.intern.veyis.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Enrollment extends BaseEntity {

    @Column(name = "PROGRAM_ID")
    private int program_id;
    @Column(name = "MENTEE_ID")
    private int mentee_id;
    @Column(name = "IS_ACTIVE")
    private boolean is_active;
    @Column(name = "MENTORCOMMENT")
    private String mentor_comment;
    @Column(name = "MENTEECOMMENT")
    private String mentee_comment;

}


