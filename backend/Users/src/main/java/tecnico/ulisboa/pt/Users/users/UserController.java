package tecnico.ulisboa.pt.Users.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import jakarta.validation.Valid;
import tecnico.ulisboa.pt.Users.users.dto.UserDto;

@RestController
public class UserController {
    
    private final Counter usersCounter;

    @Autowired
    private UserService userService;

    public UserController(MeterRegistry meterRegistry) {
        usersCounter = Counter.builder("usersCounter").description("Total number of acess to the users controller").register(meterRegistry);
    }

    @PostMapping("/user")
    public void createUser(@Valid @RequestBody UserDto userDto) {
        usersCounter.increment();
        userService.createUser(userDto);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER') and hasPermission(#username, 'USER.OWNER')")
    public UserDto getUser(@RequestParam String username) {
        usersCounter.increment();
        return userService.getUser(username);
    }
}
