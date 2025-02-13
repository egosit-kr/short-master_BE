package kr.egosit.shortmaster.global.security.auth;

import kr.egosit.shortmaster.domain.user.User;
import kr.egosit.shortmaster.domain.user.dto.UserProfileDto;
import kr.egosit.shortmaster.domain.user.repository.UserRepository;
import kr.egosit.shortmaster.global.oauth2.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        UserProfileDto userProfileDto = OAuthAttributes.extract(registrationId, attributes);
        userProfileDto.setProvider(registrationId);
        User user = saveOrUpdate(userProfileDto);

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, userProfileDto, registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())),
                customAttribute,
                userNameAttributeName
        );
    }

    private Map<String, Object> customAttribute(Map<String, Object> attributes, String userNameAttributeName, UserProfileDto userProfileDto, String registrationId) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("name", userProfileDto.getName());
        customAttribute.put("email", userProfileDto.getEmail());
        customAttribute.put("picture", userProfileDto.getPicture());

        return customAttribute;

    }

    private User saveOrUpdate(UserProfileDto userProfileDto) {

        User user = userRepository.findByEmailAndProvider(userProfileDto.getEmail(), userProfileDto.getProvider())
                .map(m -> m.update(userProfileDto.getName(), userProfileDto.getEmail(), userProfileDto.getPicture(), userProfileDto.getProvider())) // OAuth 서비스 사이트에서 유저 정보 변경이 있을 수 있기 때문에 우리 DB에도 update
                .orElse(userProfileDto.toEntity());

        return userRepository.save(user);
    }

}
