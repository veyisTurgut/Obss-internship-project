package obss.intern.veyis.manageMentorships.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.MentorshipApplicationKey;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MentorshipApplication  {

    @EmbeddedId
    MentorshipApplicationKey id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("applicant_username")
    @JoinColumn(name = "applicant_username")
    @JsonIgnoreProperties({"applicationSet"})
    private Users applicant;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("subject_id")
    @JoinColumn(name = "subject_id")
    @JsonIgnoreProperties({"applicationSet"})
    private Subject subject;

    /*
    @Id
    @Column(name = "APPLICANT_USERNAME")
    private String applicant_username;
    @Id
    @Column(name = "subject_name")
    private String subject_name;
    @Id
    @Column(name = "subsubject_name")
    private String subsubject_name;
*/

   // @Column(name = "EXPERIENCE")
    private String experience;

    //@Column(name = "IS_ACTIVE")
    private Boolean is_active;
}
