package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PhaseDTO {

    @JsonProperty("program_id")
    @NotBlank(message = "Bu faz hangi programın?!")
    private Long program_id;

    @JsonProperty("mentee_experience")
    private String mentee_experience;

    @JsonProperty("mentor_experience")
    private String mentor_experience;

    @JsonProperty("program_id")
    @Min(value = 0)
    @Max(value = 5)
    private Integer point;


    @JsonCreator
    public PhaseDTO(
            @JsonProperty("program_id") Long program_id,
            @JsonProperty("mentee_experience") String mentee_experience,
            @JsonProperty("mentor_experience") String mentor_experience,
            @JsonProperty("point") Integer point
    ) {
        this.program_id = program_id;
        this.mentee_experience = mentee_experience;
        this.mentor_experience = mentor_experience;
        this.point = point;
    }

}
