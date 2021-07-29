package obss.intern.veyis.manageMentorships.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import obss.intern.veyis.manageMentorships.entity.keysAndIds.SubjectId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(SubjectId.class)
public class Subject {

    @Id
    @Column(name = "SUBSUBJECT_NAME")
    private String subsubject_name;


    @Id
    @Column(name = "SUBJECT_NAME")
    private String  subject_name;

}
