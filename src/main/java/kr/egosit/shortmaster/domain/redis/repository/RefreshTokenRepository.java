package kr.egosit.shortmaster.domain.redis.repository;

import kr.egosit.shortmaster.domain.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByAccessToken(String accessToken);

    void deleteByRefreshToken(String refreshToken);
}
