package obss.intern.veyis.manageMentorships.entity;
import obss.intern.veyis.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users extends BaseEntity {
    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "MENTORED_PROGRAM_ID")
    private Integer mentored_program_id;


    /*@Override
    public String toString(){
        return this.username + " " + this.getId();
    }*/
}