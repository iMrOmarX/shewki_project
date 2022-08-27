package co.cotede.shewki_project.controller;

import co.cotede.shewki_project.mapper.PostMapper;
import co.cotede.shewki_project.model.PostDTO;
import co.cotede.shewki_project.model.UserInfo;
import co.cotede.shewki_project.model.Post;
import co.cotede.shewki_project.model.User;
import co.cotede.shewki_project.service.repository.PostRepository;
import co.cotede.shewki_project.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PostController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;



    @GetMapping("/post/{username}")
    public ResponseEntity<List<PostDTO>> getAllPostsOfUser(@PathVariable("username") String username) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentPrincipal = (User) authentication.getPrincipal();
//        System.out.println(currentPrincipal);
//
//        System.out.println(authentication.isAuthenticated());

//        if(!currentPrincipal.getUsername().equals(username) || !authentication.isAuthenticated()) {
//            return new ResponseEntity<>(List.of(),HttpStatus.UNAUTHORIZED);
//        }

        Optional<User> user = userRepository.getUserByUsername(username);

        if(user.isEmpty()) {
            return new ResponseEntity<>(List.of(),  HttpStatus.OK);
        }

        List<Post> posts= postRepository.getAllByAuthor(user.get());

        List<PostDTO> postsDTOS = posts.stream()
                .map(postMapper::postToPostDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postsDTOS, HttpStatus.OK);
    }


    @PostMapping("/post/{username}")
    public ResponseEntity<String> post(@PathVariable("username") String username , @RequestBody PostDTO postDTO ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        System.out.println(currentPrincipal);


        System.out.println(authentication.isAuthenticated());


        if(!currentPrincipal.getUsername().equals(username) || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("Unauthorized",HttpStatus.UNAUTHORIZED);
        }

        postRepository.save(postMapper.postDtoToPost(postDTO,currentPrincipal));

        return new ResponseEntity<>("Post has been Added", HttpStatus.OK);
    }

//
//    @GetMapping("/feed")
//    public ResponseEntity<List<Post>> getFeed(@RequestBody User user)  {
//        Optional<User> searchedUser = userRepository.getUserByUserName(user.getUsername());
//
//        if(searchedUser.isEmpty()) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//
//
//        if(!searchedUser.get().getPassword().equals(user.getPassword())) {
//            return new ResponseEntity<>( null,  HttpStatus.UNAUTHORIZED);
//        }
//
//        List<Post> posts = new ArrayList<>();
//
//        for (User s : searchedUser.get().getFollowing()) {
//            posts.addAll(postRepository.getAllByAuthor(s));
//        }
//
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }



}
