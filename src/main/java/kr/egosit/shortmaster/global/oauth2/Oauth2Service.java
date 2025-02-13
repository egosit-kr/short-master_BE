package kr.egosit.shortmaster.global.oauth2;

import kr.egosit.shortmaster.domain.user.dto.UserProfileDto;

public interface Oauth2Service {
    String getAccessToken(String code);

    UserProfileDto getUserProfile(String accessToken);
}
