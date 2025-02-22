package kr.egosit.shortmaster.domain.video;

import jakarta.persistence.*;
import kr.egosit.shortmaster.domain.user.User;
import kr.egosit.shortmaster.global.convert.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> hashTag;

    @Column
    private String url;

    @Column
    private LocalDate expiredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User users;
}
