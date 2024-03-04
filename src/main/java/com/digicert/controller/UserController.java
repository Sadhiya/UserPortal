package com.digicert.controller;

import com.digicert.entity.User;
import com.digicert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/digicert/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = {"/users"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> fetchAllUsers()
    {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping(value = {"/users/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> fetchUserById(@PathVariable(name = "id") Long userId)
    {
        Optional<User> findUser = userRepository.findById(userId);
        if(findUser.isPresent())
        {
            return ResponseEntity.ok(findUser.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = {"/users"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PutMapping(value = {"/users/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable(name = "id") Long userId,@RequestBody User user)
    {
        Optional<User> existingUser = userRepository.findById(userId);

        if (!existingUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        else {
            User userUpdate = existingUser.get();
            userUpdate.setFirstName(user.getFirstName());
            userUpdate.setLastName(user.getLastName());
            userUpdate.setEmail(user.getEmail());

            User savedUser = userRepository.save(userUpdate);
            return ResponseEntity.ok(savedUser);
        }
    }

    @DeleteMapping(value = {"/users/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUserById(@PathVariable(name = "id") Long userId)
    {
        userRepository.deleteById(userId);
        return ResponseEntity.ok("User Deleted Successfully");
    }
}
