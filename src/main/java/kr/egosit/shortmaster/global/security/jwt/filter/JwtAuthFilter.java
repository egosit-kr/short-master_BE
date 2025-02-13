package kr.egosit.shortmaster.global.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.egosit.shortmaster.domain.user.User;
import kr.egosit.shortmaster.domain.user.service.UserService;
import kr.egosit.shortmaster.global.security.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization");


        // Bearer token 검증 후 user name 조회
        if(token != null && !token.isEmpty()) {
            String jwtToken = token.substring(7);

            String email = jwtProvider.getEmailFromToken(jwtToken);
            // token 검증 완료 후 SecurityContextHolder 내 인증 정보가 없는 경우 저장
            if(email != null && !email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Spring Security Context Holder 인증 정보 set
                SecurityContextHolder.getContext().setAuthentication(getUserAuth(email));
            }
        }


        filterChain.doFilter(request,response);
    }

    /**
     * token의 사용자 idx를 이용하여 사용자 정보 조회하고, UsernamePasswordAuthenticationToken 생성
     *
     * @param email 사용자 idx
     * @return 사용자 UsernamePasswordAuthenticationToken
     */
    private UsernamePasswordAuthenticationToken getUserAuth(String email) {
        Optional<User> user = userService.findByUserId(Long.parseLong(email));

        return user.map(value -> new UsernamePasswordAuthenticationToken(value.getId(),
                "",
                Collections.singleton(new SimpleGrantedAuthority(value.getRole().name()))

        )).orElse(null);
    }

}
