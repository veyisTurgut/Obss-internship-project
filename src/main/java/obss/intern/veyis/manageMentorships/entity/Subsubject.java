package obss.intern.veyis.manageMentorships.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import obss.intern.veyis.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subsubject extends BaseEntity {
    @Column(name = "SUBSUBJECT_NAME", unique = true)
    private String subsubject_name;
    @Column(name = "SUBJECT_ID")
    private int subject_id;

}
