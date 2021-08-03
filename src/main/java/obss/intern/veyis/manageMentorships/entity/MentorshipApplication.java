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
public class MentorshipApplication {

    @EmbeddedId
    MentorshipApplicationKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("applicant_username")
    @JoinColumn(name = "applicant_username")
    @JsonIgnoreProperties({"applicationSet"})
    private Users applicant;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subject_id")
    @JoinColumn(name = "subject_id")
    @JsonIgnoreProperties({"applicationSet"})
    private Subject subject;


    @Column(name = "EXPERIENCE")
    private String experience;

    @Column(name = "STATUS")
    private String status;
    // one of the following: open - approved - rejected
}
