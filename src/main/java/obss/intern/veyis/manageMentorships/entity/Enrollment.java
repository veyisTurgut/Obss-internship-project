package obss.intern.veyis.manageMentorships.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.EnrollmentKey;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Enrollment  {

    @EmbeddedId
    EnrollmentKey id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @MapsId("program_name")
    @JoinColumn(name = "program_name")
    @JsonIgnoreProperties({"enrollmentSet"})
    private Program program;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("mentee_username")
    @JoinColumn(name = "mentee_username")
    @JsonIgnoreProperties({"enrollmentSet"})
    private Users mentee;

    private Boolean is_active;
    private String mentor_comment;
    private String mentee_comment;

}


