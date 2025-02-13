package kr.egosit.shortmaster.global.oauth2.google;

import kr.egosit.shortmaster.domain.user.dto.UserProfileDto;
import kr.egosit.shortmaster.global.oauth2.Oauth2Service;
import kr.egosit.shortmaster.global.oauth2.exception.Oauth2TokenException;
import kr.egosit.shortmaster.global.oauth2.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleOauth2Service implements Oauth2Service {
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public String getAccessToken(String code) {
        ClientRegistration google = clientRegistrationRepository.findByRegistrationId("google");
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = "https://oauth2.googleapis.com/token";

        Map<String, String> tokenRequest = new HashMap<>();
        tokenRequest.put("code", code); // 인증 코드
        tokenRequest.put("client_id", google.getClientId());
        tokenRequest.put("client_secret", google.getClientSecret());
        tokenRequest.put("redirect_uri", google.getRedirectUri());
        tokenRequest.put("grant_type", "authorization_code");
        tokenRequest.put("access_type", "offline");

        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST,
                new HttpEntity<>(tokenRequest), Map.class);

        // Access Token 추출
        String accessToken = (String) response.getBody().get("access_token");

        if(accessToken == null)
            throw new Oauth2TokenException();

        return accessToken;
    }

    @Override
    public UserProfileDto getUserProfile(String accessToken) {
        ClientRegistration google = clientRegistrationRepository.findByRegistrationId("google");
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> attributes = restTemplate.getForObject(google.getProviderDetails().getUserInfoEndpoint().getUri() +"?access_token=" + accessToken, Map.class);
        UserProfileDto userProfileDto = OAuthAttributes.extract(google.getRegistrationId(), attributes);
        userProfileDto.setProvider(google.getRegistrationId());

        return userProfileDto;
    }
}
