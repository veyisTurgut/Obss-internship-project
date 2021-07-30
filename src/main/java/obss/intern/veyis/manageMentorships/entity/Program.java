package obss.intern.veyis.manageMentorships.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Program  {
    @Id
    @Column(name = "PROGRAMNAME", unique = true)
    private String program_name;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "STARTDATE")
    private Date start_date;
    @Column(name = "ENDDATE")
    private Date end_date;

    @JsonIgnoreProperties({"program"})
    @OneToMany(mappedBy = "program")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Set<Enrollment> enrollmentSet;

    @JsonIgnoreProperties({"program"})
    @OneToMany(mappedBy = "program")
    Set<Phase> phases;
}