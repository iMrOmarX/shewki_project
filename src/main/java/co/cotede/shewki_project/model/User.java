package co.cotede.shewki_project.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId ;


    private String username;
    private String firstName;
    private String lastName;
    private String password;

    @OneToMany
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "userId"
    )
    private List<Post> posts;


    @ManyToMany
    @JoinTable(
            name="friends_map",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "userId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name="friend_id",
                    referencedColumnName = "userId"
            )
    )
    private List<User> following;
}
