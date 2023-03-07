package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.domain.YearlyGoal;
import whyzpotato.myreview.dto.GoalResponseDto;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.repository.YearlyGoalRepository;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YearlyGoalService {

    private final YearlyGoalRepository yearlyGoalRepository;
    private final UsersRepository usersRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 올해 목표 조회
     * 올해 목표, 이번달 읽은책, 올해 읽은 책, 달성율
     */
    public GoalResponseDto currentGoal(Long id) {
        int year = Year.now().getValue();
        Users users = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 시도입니다."));
        if (yearlyGoalRepository.findByUsersAndPeriod(users, year) == null) {
            // 목표가 없으면 10권으로 생성
            YearlyGoal yearlyGoal = YearlyGoal.builder().users(users).target(10).period(year).build();
            yearlyGoalRepository.save(yearlyGoal);
        }
        int target = yearlyGoalRepository.findByUsersAndPeriod(users, year).getTarget();
        Long cnt = reviewRepository.countByUserYear(users, year);
        /*
        >>> year: 2023
        >>> target: 10
        >>> cnt: 1
        >>> attainmentRate: 10.0%
         */
        return new GoalResponseDto(year, target, cnt, ((double) cnt / (double) target * 100));
    }

    /**
     * 올해 목표 수정
     */
    public GoalResponseDto updateYearlyGoal(Long id, int target) {
        int year = Year.now().getValue();
        Users users = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 시도입니다."));
        YearlyGoal yearlyGoal = yearlyGoalRepository.findByUsersAndPeriod(users, year);
        yearlyGoal.updateGoal(target);
        yearlyGoalRepository.save(yearlyGoal);
        Long cnt = reviewRepository.countByUserYear(users, year);
        return new GoalResponseDto(year, yearlyGoal.getTarget(), cnt, ((double) cnt / (double) target * 100));
    }


    /**
     * 과거 기록 조회
     */
    public List<GoalResponseDto> historyYearlyGoal(Long id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 시도입니다."));
        List<Object[]> resultList = reviewRepository.countByUserYears(users);
        /*
            >>>year, cnt: 2023, 2
            >>>year, cnt: 2022, 3
            >>>year, cnt: 2021, 1
        */

        List<GoalResponseDto> goals = resultList.stream()
                .map(m -> new GoalResponseDto((Integer) m[0], (Long) m[1]))
                .collect(Collectors.toList());

        for (GoalResponseDto goal : goals) {
            goal.setTarget(yearlyGoalRepository.findByUsersAndPeriod(users, goal.getYear()).getTarget());
            goal.setAttainmentRate((double) goal.getCnt() / (double) goal.getTarget() * 100);
        }

        return goals;
    }
}
