package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class PhaseDTO {

    @JsonProperty("program_id")
    private Long program_id;

    @JsonProperty("phase_id")
    private Long phase_id;

    @JsonProperty("mentee_experience")
    private String mentee_experience;

    @JsonProperty("mentor_experience")
    private String mentor_experience;

    @JsonProperty("mentee_point")
    @Min(0)
    @Max(5)
    private Integer mentee_point;

    @JsonProperty("mentor_point")
    @Min(0)
    @Max(5)
    private Integer mentor_point;

    @JsonProperty("end_date")
    private Date end_date;

    @JsonProperty("expected_end_date")
    private Date expected_end_date;

    @JsonProperty("start_date")
    private Date start_date;

    @JsonCreator
    public PhaseDTO(
            @JsonProperty("program_id") Long program_id,
            @JsonProperty("phase_id") Long phase_id,
            @JsonProperty("expected_end_date") Date expected_end_date,
            @JsonProperty("mentee_experience") String mentee_experience,
            @JsonProperty("mentor_experience") String mentor_experience,
            @JsonProperty("start_date") Date start_date,
            @JsonProperty("end_date") Date end_date,
            @JsonProperty("mentor_point") Integer mentor_point,
            @JsonProperty("mentee_point") Integer mentee_point
    ) {
        this.program_id = program_id;
        this.phase_id = phase_id;
        this.expected_end_date = expected_end_date;
        this.mentee_experience = mentee_experience;
        this.mentor_experience = mentor_experience;
        this.mentor_point = mentor_point;
        this.mentee_point = mentee_point;
        this.start_date = start_date;
        this.end_date = end_date;
    }

}
