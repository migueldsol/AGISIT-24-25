package tecnico.ulisboa.pt.Users.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tecnico.ulisboa.pt.Users.auth.AuthUserService;
import tecnico.ulisboa.pt.Users.auth.domain.AuthUser;
import tecnico.ulisboa.pt.Users.auth.dto.AuthDto;
import tecnico.ulisboa.pt.Users.exceptions.ErrorMessage;
import tecnico.ulisboa.pt.Users.exceptions.UserException;
import tecnico.ulisboa.pt.Users.users.domain.User;
import tecnico.ulisboa.pt.Users.users.dto.UserDto;
import tecnico.ulisboa.pt.Users.users.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository usersRepository;


    public void createUser(UserDto userDto) {
        
        if (usersRepository.findUserByUsername(userDto.getUsername()).isPresent()) {
            throw new UserException(ErrorMessage.USER_ALREADY_EXISTS, userDto.getUsername());
        }

        User user = new User(userDto.getUsername(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress(), userDto.getCity(), userDto.getCountry(), userDto.getPostalCode());

        user.setPassword(userDto.getPassword());

        usersRepository.save(user);

    }

    public UserDto getUser(String username) {
        User user = usersRepository.findUserByUsername(username).orElseThrow(() -> new UserException(ErrorMessage.USER_NOT_FOUND, username));

        return new UserDto(user);
    }
    
}
