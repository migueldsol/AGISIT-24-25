package tecnico.ulisboa.pt.Users.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tecnico.ulisboa.pt.Users.auth.domain.AuthUser;

import java.util.Optional;

@Repository
@Transactional
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    @Query(value = "select * from auth_users u where u.username = lower(:username)", nativeQuery = true)
    Optional<AuthUser> findAuthUserByUsername(String username);

    @Query(value = "select * from auth_users u where u.email = lower(:email)", nativeQuery = true)
    Optional<AuthUser> findAuthUserByEmail(String email);
}

