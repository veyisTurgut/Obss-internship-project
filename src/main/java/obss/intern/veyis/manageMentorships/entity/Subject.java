package obss.intern.veyis.manageMentorships.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Subject {

    @Id
    @GeneratedValue
    @Column(name = "SUBJECT_ID")
    private Long id;

    @Column(name = "SUBSUBJECT_NAME")
    private String subsubject_name;


    @Column(name = "SUBJECT_NAME")
    private String  subject_name;

    @JsonIgnoreProperties({"subject"})
    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<MentorshipApplication> applicationSet;

}
