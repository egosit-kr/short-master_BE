package kr.egosit.shortmaster.global.security.jwt.dto;

import kr.egosit.shortmaster.domain.redis.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    public TokenDto(RefreshToken rt) {
        this.accessToken = rt.getAccessToken();
        this.refreshToken = rt.getRefreshToken();
    }
}
