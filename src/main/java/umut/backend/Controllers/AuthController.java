package umut.backend.Controllers;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private static final String CLIENT_ID = System.getenv("SPOTIFY_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("SPOTIFY_CLIENT_SECRET");
    private static final String REDIRECT_URI = System.getenv("SPOTIFY_REDIRECT_URI");

    @GetMapping("/accessToken")
    public ResponseEntity<ClientCredentials> getAccessToken() throws ParseException, SpotifyWebApiException, IOException {
        ClientCredentialsRequest request = new SpotifyApi.Builder().setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .build()
                .clientCredentials()
                .build();
        ClientCredentials credentials = request.execute();
        return new ResponseEntity<>(credentials, HttpStatus.OK);
    }

    @GetMapping("/accessToken/{code}")
    public ResponseEntity<AuthorizationCodeCredentials> authorizeUserWithAuthorizationCode(@PathVariable String code) throws ParseException, SpotifyWebApiException, IOException {
        AuthorizationCodeRequest request = new SpotifyApi.Builder().setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(URI.create(REDIRECT_URI))
                .build()
                .authorizationCode(code)
                .build();
        AuthorizationCodeCredentials auth = request.execute();
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }
}
