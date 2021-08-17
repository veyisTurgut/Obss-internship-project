package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * This is the Data Transfer Object class of MentorshipApplication class.
 * We sent data to client in this format in order to hide important content.
 * Thus, we force users to fill vital fields with "@NotBlank" annotation.
 *
 * @see obss.intern.veyis.manageMentorships.entity.MentorshipApplication
 */
@Getter
@Setter
public class ApplicationDTO {

    @JsonProperty("applicant_username")
    @NotBlank(message = "Kullanıcı adı boş olamaz!")
    private String applicant_username;

    @JsonProperty("subject_id")
    private Long subject_id;

    @JsonProperty("subject_name")
    @NotBlank(message = "Başlık adı boş olamaz!")
    private String subject_name;

    @JsonProperty("subsubject_name")
    @NotBlank(message = "Altbaşlık adı boş olamaz!")
    private String subsubject_name;

    @JsonProperty("experience")
    private String experience;

    @JsonCreator
    public ApplicationDTO(@JsonProperty("applicant_username") String applicant_username,
                          @JsonProperty("subject_id") Long subject_id,
                          @JsonProperty("subject_name") String subject_name,
                          @JsonProperty("subsubject_name") String subsubject_name,
                          @JsonProperty("experience") String experience) {
        this.applicant_username = applicant_username;
        this.subject_name = subject_name;
        this.subject_id = subject_id;
        this.subsubject_name = subsubject_name;
        this.experience = experience;
    }
}
