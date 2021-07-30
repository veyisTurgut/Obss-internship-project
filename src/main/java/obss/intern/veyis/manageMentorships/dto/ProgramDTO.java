package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class ProgramDTO {

    @JsonProperty("programname")
    @NotBlank(message = "Program adı boş olamaz!")
    private String programname;

    @JsonProperty("enddate")
    private Date enddate;

    @JsonProperty("startdate")
    private Date startdate;

    @JsonProperty("status")
    private String status;


    @JsonCreator
    public ProgramDTO(@JsonProperty("programname") String programname,
                      @JsonProperty("enddate") Date enddate,
                      @JsonProperty("startdate") Date startdate,
                      @JsonProperty("status") String status
    ) {
        this.programname = programname;
        this.enddate = enddate;
        this.startdate = startdate;
        this.status = status;
    }
}