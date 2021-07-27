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

    @JsonProperty("gmail_address")
    @Email(message = "gmail adresi geçerli olmalı!")
    private String gmail_address;

    @JsonCreator
    public UserDTO(@JsonProperty("username") String username,
                   @JsonProperty("gmail_address") String gmail_address) {
        this.username = username;
        this.gmail_address = gmail_address;
    }
}