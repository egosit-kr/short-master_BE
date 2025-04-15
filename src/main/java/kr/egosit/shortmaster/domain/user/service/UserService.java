package kr.egosit.shortmaster.domain.user.service;

import kr.egosit.shortmaster.domain.redis.service.RefreshTokenService;
import kr.egosit.shortmaster.domain.user.User;
import kr.egosit.shortmaster.domain.user.dto.UserProfileDto;
import kr.egosit.shortmaster.domain.user.exception.UserNotFoundException;
import kr.egosit.shortmaster.domain.user.repository.UserRepository;
import kr.egosit.shortmaster.global.oauth2.Oauth2Service;
import kr.egosit.shortmaster.global.oauth2.Oauth2ServiceFactory;
import kr.egosit.shortmaster.global.security.jwt.dto.TokenDto;
import kr.egosit.shortmaster.global.security.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final Oauth2ServiceFactory oauth2ServiceFactory;

    public Optional<User> findByUserId(long id) {
        return userRepository.findById(id);
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(
                UserNotFoundException::new
        );
    }

    public TokenDto login(String code, String provider) {
        Oauth2Service oauth2Service = oauth2ServiceFactory.getOauth2Service(provider.toLowerCase());
        String oauthAccessToken = oauth2Service.getAccessToken(code);
        UserProfileDto userProfileDto = oauth2Service.getUserProfile(oauthAccessToken);

        User user = saveOrUpdate(userProfileDto);

        String accessToken = jwtProvider.generateAccessToken(user.getEmail(), Collections.singletonMap("role", user.getRole().toString()));
        String refreshToken = jwtProvider.generateRefreshToken(user.getId(), user.getEmail());
        refreshTokenService.removeUserRefreshToken(user.getId());

        return new TokenDto(refreshTokenService.uploadRefreshToken(accessToken, refreshToken, user.getId()));
    }

    @Transactional
    public void cancelAccount(long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public User saveOrUpdate(UserProfileDto userProfileDto) {

        User user = userRepository.findByEmailAndProvider(userProfileDto.getEmail(), userProfileDto.getProvider())
                .map(m -> m.update(userProfileDto.getName(), userProfileDto.getEmail(), userProfileDto.getPicture(), userProfileDto.getProvider()))
                .orElse(userProfileDto.toEntity());

        return userRepository.save(user);
    }

}
