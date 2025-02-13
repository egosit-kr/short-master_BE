package kr.egosit.shortmaster.domain.user;

import jakarta.persistence.*;
import kr.egosit.shortmaster.domain.model.BaseEntity;
import kr.egosit.shortmaster.domain.model.Role;
import kr.egosit.shortmaster.global.convert.StringListConverter;
import lombok.*;

import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringListConverter.class)
    private List<String> provider;

    private String name;

    private String email;

    private String picture;

    private boolean watermark;
    
    private int token;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User update(String name, String email, String picture, String provider) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider.add(provider);
        this.provider = this.provider.stream().distinct().toList();
        return this;
    }
}
