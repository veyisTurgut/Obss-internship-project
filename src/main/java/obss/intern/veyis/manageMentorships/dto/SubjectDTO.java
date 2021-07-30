package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SubjectDTO {

    @JsonProperty("subject_name")
    @NotBlank(message = "Konu adı boş olamaz!")
    private String subject_name;

    @JsonProperty("subsubject_name")
    @NotBlank(message = "Konu altadı boş olamaz!")
    private String subsubject_name;

    @JsonCreator
    public SubjectDTO(@JsonProperty("subject_name") String subject_name,
                      @JsonProperty("subsubject_name") String subsubject_name
    ) {
        this.subject_name = subject_name;
        this.subsubject_name = subsubject_name;
    }
}
