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
public class ApplicationSubsubjects extends BaseEntity {
    @Column(name = "SUBSUBJECT_ID", unique = true)
    private Integer subsubject_id;
    @Column(name = "APPLICATION_ID", unique = true)
    private Integer application_d;

}
