package co.cotede.shewki_project.mapper;

import co.cotede.shewki_project.model.Post;
import co.cotede.shewki_project.model.PostDTO;
import co.cotede.shewki_project.model.User;
import org.springframework.stereotype.Service;

@Service
public class PostMapper {

    public Post postDtoToPost(PostDTO postDTO, User author) {
        return Post.builder()
                .author(author)
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .build();
    }

    public PostDTO postToPostDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
