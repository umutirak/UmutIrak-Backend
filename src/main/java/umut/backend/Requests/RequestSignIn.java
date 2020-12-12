package umut.backend.Requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestSignIn {
    private String username;
    private String password;
}
