package obss.intern.veyis.manageMentorships.entity.keysAndIds;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EnrollmentKey implements Serializable {
    @Column(name= "mentee_username")
    String mentee_username;
    @Column(name= "program_name")
    String program_name;

}
