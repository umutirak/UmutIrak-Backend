package umut.backend.Services;

import lombok.AllArgsConstructor;
import umut.backend.DTOs.UserDTO;
import umut.backend.Entities.AppUser;
import umut.backend.Enums.UserRole;
import umut.backend.Mapper.AutoMapper;
import umut.backend.Repository.RolesRepository;
import umut.backend.Repository.UserRepository;
import umut.backend.Requests.RequestSignUp;
import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import umut.backend.Services.Interfaces.ICustomUserDetailsService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService, ICustomUserDetailsService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final AutoMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        List<SimpleGrantedAuthority> authorities = appUser.getUserRoles()
                .stream()
                .map(q -> new SimpleGrantedAuthority(q.getName()
                        .name()))
                .collect(Collectors.toList());
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Nullable
    @Override
    public UserDTO signUpAppUser(RequestSignUp request) {
        AppUser existingUser = userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (existingUser != null) {
            return null;
        }
        AppUser user = mapper.toAppUser(request);
        user.setUserRoles(Collections.singleton(rolesRepository.findByName(UserRole.USER)));
        return mapper.toUserDTO(userRepository.save(user));
    }
}
