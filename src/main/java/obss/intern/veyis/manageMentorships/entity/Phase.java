package obss.intern.veyis.manageMentorships.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.PhaseKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Phase {

    @EmbeddedId
    PhaseKey id;

    @Column(name = "STARTDATE")
    private Date start_date;
    @Column(name = "ENDDATE")
    private Date end_date;
    @Column(name = "POINT")
    private Integer point;
    @Column(name = "MENTOR_EXPERIENCE")
    private String mentor_experience;
    @Column(name = "MENTEE_EXPERIENCE")
    private String mentee_experience;


    @ManyToOne
    @MapsId("program_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "program_id")
    private Program program;

}