package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ApplicationDTO {

    @JsonProperty("user_id")
    @NotBlank(message = "Kullanıcı adı boş olamaz!")
    private Integer user_id;

    @JsonProperty("subject_id")
    private Integer subject_id;

    @JsonProperty("subsubject_id")
    private Integer subsubject_id;


    @JsonProperty("experience")
    private String experience;

    @JsonCreator
    public ApplicationDTO(@JsonProperty("user_id") Integer user_id,
                          @JsonProperty("subject_id") Integer subject_id,
                          @JsonProperty("subsubject_id") Integer subsubject_id,
                          @JsonProperty("experience") String experience) {
        this.user_id = user_id;
        this.subject_id = subject_id;
        this.subsubject_id = subsubject_id;
        this.experience = experience;
    }
}
