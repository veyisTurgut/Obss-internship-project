package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDTO {

    @JsonProperty("username")
    @NotBlank(message = "Kullanıcı adı boş olamaz!")
    private String username;

    @JsonProperty("program_mentored")
    private Integer program_mentored;

    @JsonCreator
    public UserDTO(@JsonProperty("username") String username
                   ,@JsonProperty("program_mentored") Integer program_mentored) {
        this.username = username;
       this.program_mentored = program_mentored;
    }
}