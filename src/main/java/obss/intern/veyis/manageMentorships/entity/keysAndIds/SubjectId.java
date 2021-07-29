package obss.intern.veyis.manageMentorships.entity.keysAndIds;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class SubjectId implements Serializable {

    private String subject_name;

    private String subsubject_name;

}
