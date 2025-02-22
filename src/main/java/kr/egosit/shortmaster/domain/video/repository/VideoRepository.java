package kr.egosit.shortmaster.domain.video.repository;

import kr.egosit.shortmaster.domain.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
