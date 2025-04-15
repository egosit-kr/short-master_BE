package kr.egosit.shortmaster.global.oauth2;

import kr.egosit.shortmaster.domain.user.dto.UserProfileDto;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attributes) -> {
        UserProfileDto memberProfile = new UserProfileDto();
        memberProfile.setName((String) attributes.get("name"));
        memberProfile.setEmail((String) attributes.get("email"));
        memberProfile.setPicture((String) attributes.get("picture"));
        return memberProfile;
    }),

    NAVER("naver", (attributes) -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        UserProfileDto memberProfile = new UserProfileDto();
        memberProfile.setName((String) response.get("name"));
        memberProfile.setEmail(((String) response.get("email")));
        memberProfile.setPicture(((String) response.get("profile_image")));
        return memberProfile;
    }),

    KAKAO("kakao", (attributes) -> {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        UserProfileDto memberProfile = new UserProfileDto();
        memberProfile.setName((String) kakaoProfile.get("nickname"));
        memberProfile.setEmail((String) kakaoAccount.get("email"));
        memberProfile.setPicture((String) kakaoProfile.get("profile_image_url"));
        return memberProfile;
    });

    private final String registrationId;
    private final Function<Map<String, Object>, UserProfileDto> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, UserProfileDto> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static UserProfileDto extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
