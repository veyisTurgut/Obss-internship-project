package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ApplicationDTO {

    @JsonProperty("applicant_username")
    @NotBlank(message = "Kullanıcı adı boş olamaz!")
    private String applicant_username;

    @JsonProperty("subject_name")
    @NotBlank(message = "Başlık adı boş olamaz!")
    private String subject_name;

    @JsonProperty("subsubject_name")
    @NotBlank(message = "Altbaşlık adı boş olamaz!")
    private String  subsubject_name;


    @JsonProperty("experience")
    private String experience;

    @JsonCreator
    public ApplicationDTO(@JsonProperty("applicant_username") String applicant_username,
                          @JsonProperty("subject_name") String subject_name,
                          @JsonProperty("subsubject_name") String subsubject_name,
                          @JsonProperty("experience") String experience) {
        this.applicant_username = applicant_username;
        this.subject_name = subject_name;
        this.subsubject_name = subsubject_name;
        this.experience = experience;
    }
}
