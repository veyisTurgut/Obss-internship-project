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
public class Users  {
    @Id
    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "MENTORED_PROGRAM_NAME")
    private String mentored_program_name;


    @JsonIgnoreProperties({"mentee"})
    @OneToMany(mappedBy = "mentee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<Enrollment> enrollmentSet;

    @JsonIgnoreProperties({"applicant"})
    @OneToMany(mappedBy = "applicant",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<MentorshipApplication> applicationSet;
    /*@Override
    public String toString(){
        return this.username + " " + this.getId();
    }*/
}