package obss.intern.veyis.manageMentorships.entity;
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

    @OneToMany(mappedBy = "mentee")
    Set<Enrollment> enrollmentSet;


    /*@Override
    public String toString(){
        return this.username + " " + this.getId();
    }*/
}