package kr.egosit.shortmaster.domain.user.service;

import kr.egosit.shortmaster.domain.user.User;
import kr.egosit.shortmaster.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByUserId(long id) {
        return userRepository.findById(id);
    }
}
