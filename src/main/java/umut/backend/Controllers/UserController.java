package umut.backend.Controllers;

import lombok.AllArgsConstructor;
import umut.backend.DTOs.UserDTO;
import umut.backend.Requests.RequestSignIn;
import umut.backend.Requests.RequestSignUp;
import umut.backend.Services.CustomUserDetailsService;
import umut.backend.Util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody RequestSignIn request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(jwtUtil.generateToken(request.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody RequestSignUp request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserDTO signedUpUser = userDetailsService.signUpAppUser(request);
        if (signedUpUser == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
