package obss.intern.veyis.manageMentorships.entity;
import obss.intern.veyis.common.BaseEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
public class Admin extends BaseEntity {
    @Column(name = "USERNAME",unique = true)
    private String username;
    @Column(name = "PASSWORD")
    private String password;

}
