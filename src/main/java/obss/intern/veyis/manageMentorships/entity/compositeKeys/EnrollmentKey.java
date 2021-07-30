package obss.intern.veyis.manageMentorships.entity.compositeKeys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(of = "id")
public class EnrollmentKey implements Serializable {
    @Column(name= "mentee_username")
    String mentee_username;
    @Column(name= "program_name")
    String program_name;

}
