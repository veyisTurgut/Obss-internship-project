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
public class Subject extends BaseEntity {
    @Column(name = "SUBJECT_NAME", unique = true)
    private String subject_name;
}
