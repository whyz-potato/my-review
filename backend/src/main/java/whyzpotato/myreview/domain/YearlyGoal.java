package whyzpotato.myreview.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "YEARLY_GOAL")
public class YearlyGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yearly_goal_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "users_id", updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users users;

    @Column
    private int period;

    @Column
    private int target;

    @Builder
    public YearlyGoal(Users users, int period, int target){
        this.users = users;
        this.period = period;
        this.target = target;
    }

    public void updateGoal(int target) {
        this.target = target;
    }

}
