package kr.egosit.shortmaster.global.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.egosit.shortmaster.domain.redis.RefreshToken;
import kr.egosit.shortmaster.domain.redis.service.RefreshTokenService;
import kr.egosit.shortmaster.domain.user.User;
import kr.egosit.shortmaster.domain.user.service.UserService;
import kr.egosit.shortmaster.global.security.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String jwt = resolveToken(request, AUTHORIZATION_HEADER);
        String refreshToken = resolveToken(request, REFRESH_TOKEN_HEADER);
        String requestURI = request.getRequestURI();

        if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
            // 유효한 액세스 토큰이 있는 경우
            Authentication authentication = jwtProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getAuthorities().stream().findFirst().get().getAuthority(), requestURI);
        } else if (StringUtils.hasText(refreshToken) && jwtProvider.validateToken(refreshToken) && jwtProvider.isRefreshToken(refreshToken)) {
            // 액세스 토큰이 만료되었고, 유효한 리프레시 토큰이 있는 경우
            Optional<RefreshToken> rt = refreshTokenService.findRefreshTokenByAccessToken(jwt);
            if (rt.isPresent() && rt.get().getRefreshToken().equals(refreshToken)) {
                User user = userService.findByUserId(rt.get().getId()).get();
                String newAccessToken = jwtProvider.generateAccessToken(user.getEmail(), Collections.singletonMap("role", user.getRole().name()));
                refreshTokenService.updateAccessToken(jwt, newAccessToken);
                response.setHeader(AUTHORIZATION_HEADER, "Bearer " + newAccessToken);

                Authentication authentication = jwtProvider.getAuthentication(newAccessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("리프레시 토큰을 이용해 새 액세스 토큰을 생성하고 Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            }
        } else {
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }
        filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
    private String resolveToken(HttpServletRequest request, String headerName) {
        String bearerToken = request.getHeader(headerName);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
