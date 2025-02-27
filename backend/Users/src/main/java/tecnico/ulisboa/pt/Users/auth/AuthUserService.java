package tecnico.ulisboa.pt.Users.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tecnico.ulisboa.pt.Users.auth.domain.AuthUser;
import tecnico.ulisboa.pt.Users.auth.dto.AuthDto;
import tecnico.ulisboa.pt.Users.auth.dto.AuthUserDto;
import tecnico.ulisboa.pt.Users.auth.repository.AuthUserRepository;
import tecnico.ulisboa.pt.Users.exceptions.ErrorMessage;
import tecnico.ulisboa.pt.Users.exceptions.UserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    Logger logger = LoggerFactory.getLogger(AuthUserService.class);


    @Transactional
    public AuthDto loginUserAuth(String username, String password) {
        AuthUser authUser = authUserRepository.findAuthUserByUsername(username).orElseThrow(() -> new UserException(ErrorMessage.USER_NOT_FOUND, username));

        logger.info("User found: " + authUser.getUsername());
        logger.info("User password: " + authUser.getPassword());
        logger.info("User input password: " + password);

        if (password == null || !authUser.getPassword().equals(password)) {
            throw new UserException(ErrorMessage.INVALID_PASSWORD);
        }

        return new AuthDto(JwtTokenProvider.generateToken(authUser), new AuthUserDto(authUser));
    }

    @Transactional
    public boolean checkConfirmationToken(String username, String token) {
        AuthUser authUser = authUserRepository.findAuthUserByUsername(username).orElseThrow(() -> new UserException(ErrorMessage.USER_NOT_FOUND, username));
        authUser.checkConfirmationToken(token);
        return true;
    }
}
