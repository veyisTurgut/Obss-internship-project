package obss.intern.veyis.manageMentorships.entity.keysAndIds;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class MentorshipApplicationId implements Serializable {

    private String subject_name;
    private String subsubject_name;
    private String applicant_username;

}
