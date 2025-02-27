package tecnico.ulisboa.pt.Users.auth;

import org.springframework.core.env.Environment;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import tecnico.ulisboa.pt.Users.auth.dto.AuthDto;
import tecnico.ulisboa.pt.Users.auth.dto.AuthPasswordDto;
import tecnico.ulisboa.pt.Users.exceptions.ErrorMessage;
import tecnico.ulisboa.pt.Users.exceptions.UserException;

import jakarta.validation.Valid;

@RestController
public class AuthUserControler {

    private final Counter authCounter;

    Logger logger = LoggerFactory.getLogger(AuthUserControler.class);
    
    @Autowired
    private AuthUserService authUserService;

    @Autowired
    Environment environment;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthUserControler(MeterRegistry registry) {
        this.authCounter = Counter.builder("authCounter").description("Total number of auth requests").register(registry);
    }

    @PostMapping("/auth/user")
    public AuthDto loginUserAuth(@Valid @RequestBody AuthPasswordDto authUsernameDto) {
        authCounter.increment();
        try {
            return authUserService.loginUserAuth(authUsernameDto.getUsername(), authUsernameDto.getPassword());
        } catch (UserException e) {
            throw new UserException(ErrorMessage.INVALID_LOGIN_CREDENTIALS);
        }
    }

    @PostMapping("/auth/confirm")
    public boolean checkToken(@RequestHeader("Authorization") String token, @Valid @RequestBody AuthDto authDto) {
        authCounter.increment();
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            return false;
        }

        logger.warn("Token: " + token);

        boolean isValid = jwtTokenProvider.validateToken(token);
        if (isValid) {
            String username = jwtTokenProvider.getUsername(token);

            logger.warn("Username token: " + username);
            logger.warn("Username authDto: " + authDto.getUser().getUsername());

            if (authDto.getUser().getUsername().equals(username)) {
                return true;
            } else {
                return false;
            }
        }else {
            return false;
        }
    }
}
