package kr.egosit.shortmaster.domain.video.service;

import kr.egosit.shortmaster.domain.user.User;
import kr.egosit.shortmaster.domain.video.Video;
import kr.egosit.shortmaster.domain.video.exception.VideoNotFoundException;
import kr.egosit.shortmaster.domain.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public Video getVideo(long videoId) {
        return videoRepository.findById(videoId).orElseThrow(
            VideoNotFoundException::new
        );
    }

    public Optional<Video> findVideo(long videoId) {
        return videoRepository.findById(videoId);
    }

    @Transactional
    public Video save(String name, String description, List<String> hashTag, String url, LocalDate expiredDate, User user) {
        Video video = Video.builder()
                .name(name)
                .description(description)
                .hashTag(hashTag)
                .url(url)
                .expiredDate(expiredDate)
                .users(user)
                .build();

        return videoRepository.save(video);
    }
}
