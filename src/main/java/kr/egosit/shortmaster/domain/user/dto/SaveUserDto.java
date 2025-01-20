package kr.egosit.shortmaster.domain.user.dto;

import kr.egosit.shortmaster.domain.model.Role;
import kr.egosit.shortmaster.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserDto {
    private String provider;
    private String email;
    private String name;
    private String picture;

    public User toEntity() {
        return User.builder()
                .provider(provider)
                .email(email)
                .name(name)
                .picture(picture)
                .token(3)
                .watermark(true)
                .role(Role.USER)
                .build();
    }
}
