package whyzpotato.myreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whyzpotato.myreview.domain.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
    Optional<Users> findByKakaoId(String kakaoId);
    Optional<Users> findBySocialId(String socialId);

}
