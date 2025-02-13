package kr.egosit.shortmaster.global.security.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${frontUrl}")
    private String RedirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();

        response.sendRedirect(UriComponentsBuilder.fromUriString(RedirectUrl)
                .queryParam("accessToken", "")
                .queryParam("refreshToken", "")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString()

        );
    }
}
