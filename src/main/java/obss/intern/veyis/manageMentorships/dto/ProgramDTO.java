package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ProgramDTO {

    @JsonProperty("program_id")
    private String program_id;

    @JsonProperty("enddate")
    private Date enddate;

    @JsonProperty("startdate")
    private Date startdate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("mentee_username")
    private String mentee_username;

    @JsonProperty("mentor_username")
    private String mentor_username;

    @JsonProperty("phases")
    private Set<String> phases;

    @JsonCreator
    public ProgramDTO(@JsonProperty("program_id") String program_id,
                      @JsonProperty("enddate") Date enddate,
                      @JsonProperty("startdate") Date startdate,
                      @JsonProperty("status") String status,
                      @JsonProperty("mentee_username") String mentee_username,
                      @JsonProperty("mentor_username") String mentor_username,
                      @JsonProperty("phases") Set<String> phases

                      ) {
        this.program_id = program_id;
        this.enddate = enddate;
        this.startdate = startdate;
        this.status = status;
        this.mentee_username = mentee_username;
        this.mentor_username = mentor_username;
        this.phases = phases;
    }
}