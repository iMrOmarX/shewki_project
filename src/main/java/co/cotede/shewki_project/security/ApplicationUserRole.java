package co.cotede.shewki_project.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static co.cotede.shewki_project.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(STUDENT_WRITE,STUDENT_READ)),
    USER(Sets.newHashSet(POST_READ));


    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorites(String username) {
        Set<SimpleGrantedAuthority> permissions =this.permissions.stream()
                .map(e-> new SimpleGrantedAuthority(e.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        permissions.add(new SimpleGrantedAuthority("ROLE_" + username ));
        return permissions;
    }
}
