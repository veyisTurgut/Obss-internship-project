package obss.intern.veyis.manageMentorships.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.MentorshipApplicationKey;

import javax.persistence.*;


/**
 * This is the entity class of Mentorship Applications. It automatically creates corresponding
 * database table and foreign key relations between tables.
 * <p>
 * Key of this table is composite --composition of applicant and subject. So, an EmbeddedId is used.
 *
 * @see MentorshipApplicationKey
 */
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

    /////// These two are included because Elasticsearch cant join two tables.
    /////// These information are much needed in the client side.
    @Column(name = "subject_name")
    private String subject_name;

    @Column(name = "subsubject_name")
    private String subsubject_name;
    ///////////


    @Column(name = "EXPERIENCE", length = 2048)
    private String experience;

    @Column(name = "STATUS")
    private String status;
    // one of the following: open - approved - rejected - full(when 2 mentee enrolled)
}
