package co.cotede.shewki_project.service.repository;

import co.cotede.shewki_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> getUserByUserName(String userName);
}
