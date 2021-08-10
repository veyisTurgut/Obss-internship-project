package obss.intern.veyis.manageMentorships.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.PhaseKey;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.ProgramId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Phase implements Comparable<Phase> {

    @EmbeddedId
    PhaseKey id;

    @Column(name = "START_DATE")
    private Date start_date;
    @Column(name = "END_DATE")
    private Date end_date;
    @Column(name = "MENTOR_POINT")
    private Integer mentor_point;
    @Column(name = "MENTEE_POINT")
    private Integer mentee_point;
    @Column(name = "MENTOR_EXPERIENCE")
    private String mentor_experience;
    @Column(name = "MENTEE_EXPERIENCE")
    private String mentee_experience;


    @ManyToOne
    @MapsId("program_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "program_id")
    private Program program;


    @Override
    public int compareTo(Phase phase) {
        if (this.getId().getPhase_id() == null || phase.getId().getPhase_id() == null) {
            return 0;
        }
        return this.getId().getPhase_id().compareTo(phase.getId().getPhase_id());
    }
}