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
public class MentorshipApplication extends BaseEntity {
    @Column(name = "APPLICANT_ID")
    private int applicant_userid;
    @Column(name = "EXPERIENCE")
    private String experience;
    @Column(name = "IS_ACTIVE")
    private boolean is_active;
}
