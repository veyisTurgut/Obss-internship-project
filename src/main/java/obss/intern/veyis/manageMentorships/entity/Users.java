package obss.intern.veyis.manageMentorships.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Users {
    @Id
    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "GMAIl", unique = true)
    private String gmail;
    @JsonIgnoreProperties({"mentor"})
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Program> programsMentored;

    @JsonIgnoreProperties({"mentee"})
    @OneToMany(mappedBy = "mentee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Program> programsMenteed;

    @JsonIgnoreProperties({"applicant"})
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<MentorshipApplication> applicationSet;

}
