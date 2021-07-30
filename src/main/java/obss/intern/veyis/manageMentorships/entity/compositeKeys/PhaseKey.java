package obss.intern.veyis.manageMentorships.entity.compositeKeys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import obss.intern.veyis.manageMentorships.entity.Program;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class PhaseKey implements Serializable {
    @Column(name= "PHASE_NAME")
    private String phase_name;

    @Column(name= "program_id")
    private ProgramId program_id;

}
