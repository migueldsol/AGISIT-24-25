package tecnico.ulisboa.pt.Users.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import tecnico.ulisboa.pt.Users.users.domain.User;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from users u where u.username = lower(:username)", nativeQuery = true)
    Optional<User> findUserByUsername(String username);

    @Query(value = "select * from users u where u.email = lower(:email)", nativeQuery = true)
    Optional<User> findUserByEmail(String email);
    
}
