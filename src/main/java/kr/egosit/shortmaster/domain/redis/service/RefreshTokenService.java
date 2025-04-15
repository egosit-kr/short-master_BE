package kr.egosit.shortmaster.domain.redis.service;

import kr.egosit.shortmaster.domain.redis.RefreshToken;
import kr.egosit.shortmaster.domain.redis.repository.RefreshTokenRepository;
import kr.egosit.shortmaster.global.security.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public Optional<RefreshToken> findRefreshTokenByAccessToken(String accessToken) {
        return refreshTokenRepository.findByAccessToken(accessToken);
    }

    public Optional<RefreshToken> findByUserId(long userId) {
        return refreshTokenRepository.findById(userId);
    }

    public RefreshToken updateAccessToken(String oldAccessToken, String newAccessToken) {
        Optional<RefreshToken> refreshTokenOpt = findRefreshTokenByAccessToken(oldAccessToken);

        if (refreshTokenOpt.isEmpty())
            return null;

        RefreshToken refreshToken = refreshTokenOpt.get();
        refreshToken.updateAccessToken(newAccessToken);

        return refreshTokenRepository.save(refreshToken);
    }

    public void removeUserRefreshToken(long userId) {
        refreshTokenRepository.deleteById(userId);
    }

    public void removeUserRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    public void removeRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    public RefreshToken uploadRefreshToken(String accessToken, String refreshToken, long userId) {
        return refreshTokenRepository.save(RefreshToken.builder()
                .id(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
    }
}
