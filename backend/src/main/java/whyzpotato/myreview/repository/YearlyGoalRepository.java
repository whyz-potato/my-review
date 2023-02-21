package whyzpotato.myreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.domain.YearlyGoal;

import java.util.List;

public interface YearlyGoalRepository extends JpaRepository<YearlyGoal, Long> {

    public YearlyGoal findByUsersAndPeriod(Users users, int period);
    public List<YearlyGoal> findAllByUsers(Users users);
}
