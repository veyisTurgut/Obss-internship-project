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

    @JsonProperty("email")
    private String email;


    @JsonCreator
    public UserDTO(@JsonProperty("username") String username, @JsonProperty("email") String email) {
        this.username = username;
        this.email = email;
    }
}