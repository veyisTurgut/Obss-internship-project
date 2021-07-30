package obss.intern.veyis.manageMentorships.entity.compositeKeys;

import lombok.EqualsAndHashCode;
import obss.intern.veyis.manageMentorships.entity.Subject;
import obss.intern.veyis.manageMentorships.entity.Users;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(of = "id")
public class MentorshipApplicationKey implements Serializable {

    @Column(name= "subject_id")

    private Long subject_id;

    /*
    private String subject_name;
    private String subsubject_name;
     */

    @Column(name= "applicant_username")
    private String applicant_username;

}
