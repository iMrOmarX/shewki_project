package co.cotede.shewki_project.controller;

import co.cotede.shewki_project.mapper.UserMapper;
import co.cotede.shewki_project.model.User;
import co.cotede.shewki_project.model.UserDTO;
import co.cotede.shewki_project.service.repository.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @PostMapping("/user")
    public void addUser(@RequestBody UserDTO user) {
        userRepository.save(userMapper.convertDTOtoUser(user));
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam int id) {
        userRepository.deleteById(Long.valueOf(id));
    }

    @PatchMapping("/user")
    public User updateUser(@RequestBody User user) {
        Optional<User> oldUser = userRepository.findById(user.getUserId());
        oldUser.ifPresent((e) -> {
            if(user.getFirstName() != null) e.setFirstName(user.getFirstName());
            if(user.getLastName() != null) e.setLastName(user.getLastName());
            if(user.getPassword() != null) e.setPassword(user.getPassword());
            if(user.getUsername() != null) e.setUsername(user.getUsername());

            userRepository.save(e);
        });

        return oldUser.get();
    }


    @PostMapping("/friends")
    public ResponseEntity<String> addFriend(@RequestParam("added_username") String addedUsername) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipal = (User) authentication.getPrincipal();
        System.out.println(currentPrincipal);
        System.out.println(authentication.isAuthenticated());

        if(!authentication.isAuthenticated())
            return new ResponseEntity<>("" , HttpStatus.UNAUTHORIZED);


        Optional<User> followedUser = userRepository.getUserByUsername(addedUsername);

        if(followedUser.isEmpty()){
            return new ResponseEntity<>("Followed User Not Found" , HttpStatus.NOT_FOUND);
        }

        currentPrincipal.getFollowing().add(followedUser.get());

        userRepository.save(currentPrincipal);

        return new ResponseEntity<>("Follow has been Added", HttpStatus.OK);
    }




}
