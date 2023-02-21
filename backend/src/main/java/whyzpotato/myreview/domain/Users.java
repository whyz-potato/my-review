package whyzpotato.myreview.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue
    @Column(name = "users_id")
    private Long id;

    private String name;

    public static Users createUser(String name){
        Users users = new Users();
        users.setName(name);
        return users;
    }
}
