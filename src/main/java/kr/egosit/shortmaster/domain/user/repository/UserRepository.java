package kr.egosit.shortmaster.domain.user.repository;

import kr.egosit.shortmaster.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
