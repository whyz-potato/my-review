package whyzpotato.myreview.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String pw;

    @Column
    @CreatedDate
    private LocalDateTime createDate;

    @Column
    @LastModifiedDate
    private LocalDateTime deleteDate;

    @Column(length = 200)
    private String snsAccessToken;

    @Column
    private String profileImage;

    @OneToMany(mappedBy = "users")
    private List<YearlyGoal> yearlyGoalList = new ArrayList<>();

    @Builder
    public Users(String email, String name, String pw, LocalDateTime createDate) {
        this.email = email;
        this.name = name;
        this.pw = pw;   //TODO 암호화
        this.createDate = createDate;
    }
    
}
