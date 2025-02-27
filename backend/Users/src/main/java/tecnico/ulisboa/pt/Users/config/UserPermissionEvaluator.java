package tecnico.ulisboa.pt.Users.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import tecnico.ulisboa.pt.Users.auth.domain.AuthUser;
import tecnico.ulisboa.pt.Users.auth.repository.AuthUserRepository;

import java.io.Serializable;

@Component
public class UserPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private AuthUserRepository authUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserPermissionEvaluator.class);


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        AuthUser authUser = ((AuthUser) authentication.getPrincipal());

        logger.warn("Checking permission for user: " + authUser.getUsername());

        if (targetDomainObject instanceof String) {
            String username = (String) targetDomainObject;
            String permissionValue = (String) permission;
            switch (permissionValue) {
                case "USER.OWNER":
                    AuthUser temporaryAuthUser = authUserRepository.findAuthUserByUsername(username).orElse(null);
                    if (temporaryAuthUser == null) return false;
                    return temporaryAuthUser.getUsername().equals(authUser.getUsername());
                default:
                    return false;
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

}
