package obss.intern.veyis.manageMentorships.entity.compositeKeys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Since each phase belongs to only one program and their phase_id may be same, I created this composite key.
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class PhaseKey implements Serializable {
    @Column(name = "PHASE_ID")
    private Long phase_id;

    @Column(name = "program_id")
    private ProgramId program_id;

}
