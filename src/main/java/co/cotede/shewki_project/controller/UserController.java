package co.cotede.shewki_project.controller;

import co.cotede.shewki_project.mapper.UserMapper;
import co.cotede.shewki_project.model.User;
import co.cotede.shewki_project.model.UserDTO;
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
    public ResponseEntity<String> addFriend(@RequestBody ObjectNode objectNode) {


        String userName = objectNode.get("userName").asText();
        String password = objectNode.get("password").asText();
        String followedUserName =objectNode.get("followedUserName").asText();

        Optional<User> searchedUser = userRepository.getUserByUsername(userName);

        if(searchedUser.isEmpty()) {
            return new ResponseEntity<>("User not Found", HttpStatus.UNAUTHORIZED);
        }

        if(!searchedUser.get().getPassword().equals(password)) {
            return new ResponseEntity<>("User not Found",  HttpStatus.UNAUTHORIZED);
        }

        Optional<User> followedUser = userRepository.getUserByUsername(followedUserName);

        if(followedUser.isEmpty()){
            return new ResponseEntity<>("Followed User Not Found" , HttpStatus.NOT_FOUND);
        }

        searchedUser.get().getFollowing().add(followedUser.get());

        userRepository.save(searchedUser.get());

        return new ResponseEntity<>("Follow has been Added", HttpStatus.OK);
    }




}
