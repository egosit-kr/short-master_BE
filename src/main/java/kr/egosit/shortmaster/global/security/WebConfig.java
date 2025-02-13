package kr.egosit.shortmaster.global.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // CORS 설정
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5500", "https://localhost:5500", "*") // 허용할 출처
                .allowedMethods("GET", "POST", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("Content-Type", "Authorization") // 허용할 헤더
                .exposedHeaders("access-token", "refresh-token")
                .allowCredentials(true); // 자격 증명(쿠키 등)을 허용
    }
}
