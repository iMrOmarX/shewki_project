package co.cotede.shewki_project.controller;

import co.cotede.shewki_project.model.User;
import co.cotede.shewki_project.service.repository.UserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @PostMapping("/user")
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
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
            if(user.getUserName() != null) e.setUserName(user.getUserName());

            userRepository.save(e);
        });

        return oldUser.get();
    }


    @PostMapping("/friends")
    public ResponseEntity<String> addFriend(@RequestBody ObjectNode objectNode) {


        String userName = objectNode.get("userName").asText();
        String password = objectNode.get("password").asText();
        String followedUserName =objectNode.get("followedUserName").asText();

        Optional<User> searchedUser = userRepository.getUserByUserName(userName);

        if(searchedUser.isEmpty()) {
            return new ResponseEntity<>("User not Found", HttpStatus.UNAUTHORIZED);
        }

        if(!searchedUser.get().getPassword().equals(password)) {
            return new ResponseEntity<>("User not Found",  HttpStatus.UNAUTHORIZED);
        }

        Optional<User> followedUser = userRepository.getUserByUserName(followedUserName);

        if(followedUser.isEmpty()){
            return new ResponseEntity<>("Followed User Not Found" , HttpStatus.NOT_FOUND);
        }

        searchedUser.get().getFollowing().add(followedUser.get());

        userRepository.save(searchedUser.get());

        return new ResponseEntity<>("Follow has been Added", HttpStatus.OK);
    }




}
