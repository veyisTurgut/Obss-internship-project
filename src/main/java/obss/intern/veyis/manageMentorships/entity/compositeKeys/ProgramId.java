package obss.intern.veyis.manageMentorships.entity.compositeKeys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@Getter
@Setter
public class ProgramId implements Serializable {

    @Column(name = "PROGRAM_ID")
    @GeneratedValue
    private Long program_id;

}
