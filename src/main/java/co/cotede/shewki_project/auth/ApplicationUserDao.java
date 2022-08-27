package co.cotede.shewki_project.auth;

import co.cotede.shewki_project.model.User;

import java.util.Optional;

public interface ApplicationUserDao {
    public Optional<User> selectApplicationUserByUsername(String username);
}
