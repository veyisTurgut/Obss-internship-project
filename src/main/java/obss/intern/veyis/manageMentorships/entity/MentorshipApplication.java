package obss.intern.veyis.manageMentorships.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import obss.intern.veyis.manageMentorships.entity.keysAndIds.MentorshipApplicationId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(MentorshipApplicationId.class)
public class MentorshipApplication  {

    @Id
    @Column(name = "APPLICANT_USERNAME")
    private String applicant_username;

    @Id
    @Column(name = "subject_name")
    private String subject_name;
    @Id
    @Column(name = "subsubject_name")
    private String subsubject_name;

    @Column(name = "EXPERIENCE")
    private String experience;

    @Column(name = "IS_ACTIVE")
    private Boolean is_active;
}
