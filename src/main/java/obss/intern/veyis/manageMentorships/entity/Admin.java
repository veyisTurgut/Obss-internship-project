package obss.intern.veyis.manageMentorships.entity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Admin  {
    @Id
    @Column(name = "USERNAME",unique = true)
    private String username;
    @Column(name = "PASSWORD")
    private String password;

}
