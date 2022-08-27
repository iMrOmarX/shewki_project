package co.cotede.shewki_project.service.repository;

import co.cotede.shewki_project.model.Post;
import co.cotede.shewki_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,  Long> {
    public List<Post> getAllByAuthor(User author) ;

}
