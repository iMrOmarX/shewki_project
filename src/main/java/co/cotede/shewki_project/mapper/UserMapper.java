package co.cotede.shewki_project.mapper;

import co.cotede.shewki_project.model.User;
import co.cotede.shewki_project.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static co.cotede.shewki_project.security.ApplicationUserRole.USER;

@Service
public class UserMapper {

    @Autowired
    PasswordEncoder passwordEncoder ;

    public User convertDTOtoUser(UserDTO userDTO) {
        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(USER)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .isCredentialsNonExpired(true)
                .build();
    }

}
