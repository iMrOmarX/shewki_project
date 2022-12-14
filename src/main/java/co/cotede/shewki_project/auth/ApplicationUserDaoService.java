package co.cotede.shewki_project.auth;

import co.cotede.shewki_project.model.User;
import co.cotede.shewki_project.service.repository.UserRepository;
import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static co.cotede.shewki_project.security.ApplicationUserRole.ADMIN;


@Repository("usersRepo")
public class ApplicationUserDaoService implements ApplicationUserDao{


    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> selectApplicationUserByUsername(String username) {
        Optional<User> user = userRepository.getUserByUsername(username);
        return user;
    }


}
