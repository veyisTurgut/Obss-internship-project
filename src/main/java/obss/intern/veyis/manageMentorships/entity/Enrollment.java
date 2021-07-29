package obss.intern.veyis.manageMentorships.entity;
import lombok.Getter;
import obss.intern.veyis.manageMentorships.entity.keysAndIds.EnrollmentKey;

import javax.persistence.*;

@Entity
@Getter
public class Enrollment  {

    @EmbeddedId
    EnrollmentKey id;

    @ManyToOne
    @MapsId("program_name")
    @JoinColumn(name = "program_name")
    private Program program;

    @ManyToOne
    @MapsId("mentee_username")
    @JoinColumn(name = "MENTEE_NAME")
    private Users mentee;

    private Boolean is_active;
    private String mentor_comment;
    private String mentee_comment;

}


