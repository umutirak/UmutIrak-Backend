package umut.backend.Services.Interfaces;

import umut.backend.DTOs.UserDTO;
import umut.backend.Requests.RequestSignUp;

public interface ICustomUserDetailsService {
    UserDTO signUpAppUser(RequestSignUp request);
}
