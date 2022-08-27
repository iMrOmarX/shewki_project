package co.cotede.shewki_project.controller;

import co.cotede.shewki_project.model.UserInfo;
import co.cotede.shewki_project.model.Post;
import co.cotede.shewki_project.model.User;
import co.cotede.shewki_project.service.repository.PostRepository;
import co.cotede.shewki_project.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;




    @GetMapping("/post")
    public ResponseEntity<List<Post>> getAllPostsOfUser(@RequestBody UserInfo user) {
        Optional<User> searchedUser = userRepository.getUserByUserName(user.getUserName());

        if(searchedUser.isEmpty()) {
            return new ResponseEntity<>(List.of() , HttpStatus.NOT_FOUND);
        }

        if(!searchedUser.get().getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(List.of(),  HttpStatus.UNAUTHORIZED);
        }

        List<Post> posts= postRepository.getAllByAuthor(searchedUser.get());

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody UserInfo user ) {
        Optional<User> searchedUser = userRepository.getUserByUserName(user.getUserName());

        if(searchedUser.isEmpty()) {
            return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
        }


        System.out.println(user.getPassword());
        System.out.println(searchedUser.get().getPassword().equals(user.getPassword()));
        if(!searchedUser.get().getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>("User not Found",  HttpStatus.UNAUTHORIZED);
        }

        Post newPost = Post.builder()
                .title(user.getAddedPost().getTitle())
                .content(user.getAddedPost().getContent())
                .author(searchedUser.get())
                .build();

        postRepository.save(newPost);
        return new ResponseEntity<>("Post has been Added", HttpStatus.OK);
    }


    @GetMapping("/feed")
    public ResponseEntity<List<Post>> getFeed(@RequestBody User user)  {
        Optional<User> searchedUser = userRepository.getUserByUserName(user.getUserName());

        if(searchedUser.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }


        if(!searchedUser.get().getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>( null,  HttpStatus.UNAUTHORIZED);
        }

        List<Post> posts = new ArrayList<>();

        for (User s : searchedUser.get().getFollowing()) {
            posts.addAll(postRepository.getAllByAuthor(s));
        }

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


}
