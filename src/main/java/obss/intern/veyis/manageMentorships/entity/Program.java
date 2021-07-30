package obss.intern.veyis.manageMentorships.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    Set<Enrollment> enrollmentSet;

    @JsonIgnoreProperties({"program"})
    @OneToMany(mappedBy = "program")
    Set<Phase> phases;
}