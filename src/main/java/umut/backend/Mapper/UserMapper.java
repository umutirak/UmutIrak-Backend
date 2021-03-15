package umut.backend.Mapper;

import org.mapstruct.Mapper;
import umut.backend.DTOs.UserDTO;
import umut.backend.Entities.AppUser;
import umut.backend.Requests.RequestSignUp;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AppUser toAppUser(RequestSignUp request);

    UserDTO fromAppUser(AppUser user);
}
