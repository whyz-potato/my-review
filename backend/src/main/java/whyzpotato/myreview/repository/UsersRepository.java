package whyzpotato.myreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whyzpotato.myreview.domain.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    public Users findByEmail(String email);

}
