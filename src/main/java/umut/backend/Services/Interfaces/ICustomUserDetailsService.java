package umut.backend.Services.Interfaces;

import umut.backend.DTOs.UserDTO;
import umut.backend.Entities.Role;
import umut.backend.Requests.RequestSignUp;

import java.util.Set;

public interface ICustomUserDetailsService {
    UserDTO signUpAppUser(RequestSignUp request);

    Set<Role> findUserRoles(String username);
}
