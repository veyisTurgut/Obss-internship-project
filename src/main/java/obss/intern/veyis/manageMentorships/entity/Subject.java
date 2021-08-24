package obss.intern.veyis.manageMentorships.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Subject {

    @Id
    @GeneratedValue
    @Column(name = "SUBJECT_ID")
    private Long subject_id;

    @Column(name = "SUBSUBJECT_NAME")
    private String subsubject_name;


    @Column(name = "SUBJECT_NAME")
    private String subject_name;

    @JsonIgnoreProperties({"subject"})
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<MentorshipApplication> applicationSet;

    @JsonIgnoreProperties({"subject"})
    @OneToMany(mappedBy = "subject", /*this may be deleted*/cascade = CascadeType.ALL,/**/ fetch = FetchType.LAZY)
    Set<Program> programsSet;

}
