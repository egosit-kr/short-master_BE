package kr.egosit.shortmaster.domain.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import kr.egosit.shortmaster.domain.user.dto.OauthLoginDto;
import kr.egosit.shortmaster.domain.user.service.UserService;
import kr.egosit.shortmaster.global.basic.response.BasicResponse;
import kr.egosit.shortmaster.global.security.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/authorize/{provider}")
    public void redirectToProvider(@PathVariable("provider") String provider, HttpServletResponse resp) throws IOException {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(provider.toLowerCase());
        String authUrl = clientRegistration.getProviderDetails().getAuthorizationUri()
                +"?client_id="+ clientRegistration.getClientId()
                +"&redirect_uri="+ "http://localhost:5500/login/callback"
                +"&response_type=code&scope="+ String.join("", clientRegistration.getScopes());
        resp.sendRedirect(authUrl);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody OauthLoginDto oauthLoginDto) {
        TokenDto token = userService.login(oauthLoginDto.getCode(), oauthLoginDto.getProvider());

        HttpHeaders headers = new HttpHeaders();

        headers.set("Access-Token", token.getAccessToken());
        headers.set("Refresh-Token", token.getRefreshToken());

        return BasicResponse.ok("정상적으로 로그인 되었습니다.", headers);
    }
}
