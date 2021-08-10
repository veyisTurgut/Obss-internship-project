package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ProgramDTO {

    @JsonProperty("program_id")
    private Long program_id;

    @JsonProperty("end_date")
    private Date end_date;

    @JsonProperty("start_date")
    private Date start_date;

    @JsonProperty("status")
    private String status;

    @JsonProperty("mentee_username")
    @NotNull(message = "Mentee adı olmak zorunda!")
    private String mentee_username;

    @JsonProperty("mentor_username")
    @NotNull(message = "Mentor adı olmak zorunda!")
    private String mentor_username;

    @JsonProperty("mentor_comment")
    private String mentor_comment;

    @JsonProperty("mentee_comment")
    private String mentee_comment;

    @JsonProperty("subject_name")
    @NotNull(message = "Konu başlığı olmak zorunda!")
    private String subject_name;

    @JsonProperty("subsubject_name")
    @NotNull(message = "Alt konu başlığı olmak zorunda!")
    private String subsubject_name;


    @JsonProperty("phases")
    private Set<PhaseDTO> phases;

    @JsonCreator
    public ProgramDTO(@JsonProperty("program_id") Long program_id,
                      @JsonProperty("end_date") Date end_date,
                      @JsonProperty("start_date") Date start_date,
                      @JsonProperty("status") String status,
                      @JsonProperty("mentee_username") String mentee_username,
                      @JsonProperty("mentor_username") String mentor_username,
                      @JsonProperty("subsubject_name") String subsubject_name,
                      @JsonProperty("subject_name") String subject_name,
                      @JsonProperty("phases") Set<PhaseDTO> phases,
                      @JsonProperty("mentor_comment") String mentor_comment,
                      @JsonProperty("mentee_comment") String mentee_comment


    ) {
        this.program_id = program_id;
        this.end_date = end_date;
        this.start_date = start_date;
        this.status = status;
        this.mentee_username = mentee_username;
        this.mentor_username = mentor_username;
        this.subsubject_name = subsubject_name;
        this.phases = phases;
        this.mentor_comment = mentor_comment;
        this.mentee_comment = mentee_comment;
        this.subject_name = subject_name;
    }
}