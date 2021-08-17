package obss.intern.veyis.manageMentorships.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDTO {

    @JsonProperty("username")
    @NotBlank(message = "Kullanıcı adı boş olamaz!")
    private String username;

    @JsonProperty("gmail")
    @Email
    private String gmail;


    @JsonCreator
    public UserDTO(@JsonProperty("username") String username, @JsonProperty("gmail") String gmail) {
        this.username = username;
        this.gmail = gmail;
    }
}