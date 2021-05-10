package umut.backend.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends BaseDTO {
    private String username;
    private String email;
    private Date createDate;
    private String name;
    private String surname;
    private Date dateOfBirth;
}
