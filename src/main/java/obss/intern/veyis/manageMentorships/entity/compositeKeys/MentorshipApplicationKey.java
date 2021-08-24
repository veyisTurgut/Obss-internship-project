package obss.intern.veyis.manageMentorships.entity.compositeKeys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


/**
 * Composite key of Mentorship Application consisting of a Subject and a User.
 * @see obss.intern.veyis.manageMentorships.entity.MentorshipApplication
 */
@Embeddable
@EqualsAndHashCode(of = "id")
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class MentorshipApplicationKey implements Serializable {

    @Column(name = "subject_id")
    private Long subject_id;

    @Column(name = "applicant_username")
    private String applicant_username;

}
