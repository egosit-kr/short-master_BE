package kr.egosit.shortmaster.domain.user.repository;

import kr.egosit.shortmaster.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN u.provider p WHERE u.email = :email AND p = :provider")
    Optional<User> findByEmailAndProvider(@Param("email") String email, @Param("provider") String provider);

}
