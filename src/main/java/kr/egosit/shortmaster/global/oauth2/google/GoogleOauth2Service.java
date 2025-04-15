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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
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
        RestClient restClient = RestClient.create();
        String tokenUrl = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> tokenRequest = new LinkedMultiValueMap<>();
        tokenRequest.add("code", code); // 인증 코드
        tokenRequest.add("client_id", google.getClientId());
        tokenRequest.add("client_secret", google.getClientSecret());
        tokenRequest.add("redirect_uri", google.getRedirectUri());
        tokenRequest.add("grant_type", "authorization_code");
//        tokenRequest.add("access_type", "offline");

        Map<String, Object> response = restClient.post()
                .uri(tokenUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(tokenRequest)
                .retrieve()
                .body(Map.class);

        // Access Token 추출

        if(!response.containsKey("access_token") || response.get("access_token") == null)
            throw new Oauth2TokenException();

        String accessToken = response.get("access_token").toString();

        return accessToken;
    }

    @Override
    public UserProfileDto getUserProfile(String accessToken) {
        ClientRegistration google = clientRegistrationRepository.findByRegistrationId("google");
        RestClient restClient = RestClient.create();

        Map<String, Object> attributes = restClient.get()
                .uri(google.getProviderDetails().getUserInfoEndpoint().getUri())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(Map.class);

        UserProfileDto userProfileDto = OAuthAttributes.extract(google.getRegistrationId().toLowerCase(), attributes);
        userProfileDto.setProvider(google.getRegistrationId());

        return userProfileDto;
    }
}
