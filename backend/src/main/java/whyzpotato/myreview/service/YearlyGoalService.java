package whyzpotato.myreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whyzpotato.myreview.domain.Users;
import whyzpotato.myreview.domain.YearlyGoal;
import whyzpotato.myreview.repository.ReviewRepository;
import whyzpotato.myreview.repository.UsersRepository;
import whyzpotato.myreview.repository.YearlyGoalRepository;

import java.time.Year;

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
    public void currentGoal(Long id) {
        int year = Year.now().getValue();
        Users users = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 시도입니다."));
        if (yearlyGoalRepository.findByUsersAndPeriod(users, year) == null) {
            // 목표가 없으면 10권으로 생성
            YearlyGoal yearlyGoal = YearlyGoal.builder().users(users).target(10).period(year).build();
            yearlyGoalRepository.save(yearlyGoal);
        }
        int target = yearlyGoalRepository.findByUsersAndPeriod(users, year).getTarget();
        Long reviewCnt = reviewRepository.countByUserYear(users, year);
        System.out.println(">>> year: " + year);
        System.out.println(">>> target: " + target);
        System.out.println(">>> reviewCnt: " + reviewCnt);
        System.out.println(">>> attainmentRate: " + (double) reviewCnt / (double) target * 100 + "%");
        /*
        >>> year: 2023
        >>> target: 10
        >>> reviewCnt: 1
        >>> attainmentRate: 10.0%
         */
    }

    /**
     * 올해 목표 수정
     */
    public void updateYearlyGoal(Long id, int target) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 시도입니다."));
        YearlyGoal yearlyGoal = yearlyGoalRepository.findByUsersAndPeriod(users, Year.now().getValue());
        yearlyGoal.updateGoal(target);
        yearlyGoalRepository.save(yearlyGoal);
    }


    /**
     * 과거 기록 조회
     */
}
