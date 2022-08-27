package co.cotede.shewki_project.model;


import co.cotede.shewki_project.security.ApplicationUserRole;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_user")
public class User implements UserDetails {
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
    @ToString.Exclude
    private List<Post> posts;


    @ManyToMany(fetch = FetchType.EAGER)
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


    private ApplicationUserRole role;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled ;


    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorites(this.username);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
