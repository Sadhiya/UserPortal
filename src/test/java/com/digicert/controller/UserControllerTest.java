package com.digicert.controller;

import com.digicert.entity.User;
import com.digicert.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        userController = new UserController();
        userRepository = Mockito.mock(UserRepository.class);

        ReflectionTestUtils.setField(userController,"userRepository",userRepository);
    }

    @Test
    void fetchAllUsers() {
        List<User> userList = getUserList();
        when(userRepository.findAll())
                .thenReturn(userList);
        ResponseEntity<List<User>> actualResponse = userController.fetchAllUsers();
        assertEquals(ResponseEntity.ok(userList),actualResponse);
    }

    @Test
    void fetchUserById() {
        User user = getUser();
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        ResponseEntity<User> actualResponse = userController.fetchUserById(1L);
        assertEquals(ResponseEntity.ok(user),actualResponse);
    }

    @Test
    void fetchUserByIdNotFound() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        ResponseEntity<User> actualResponse = userController.fetchUserById(1L);
        assertEquals(ResponseEntity.notFound().build(),actualResponse);
    }

    @Test
    void createNewUser() {
        User user = getUser();
        when(userRepository.save(any(User.class)))
                .thenReturn(user);
        ResponseEntity<User> actualResponse = userController.createNewUser(user);
        assertEquals(ResponseEntity.ok(user),actualResponse);
    }

    @Test
    void updateUser() {
        User user = getUser();
        User updatedUser = user;
        updatedUser.setFirstName("Jane");

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class)))
                .thenReturn((updatedUser));

        ResponseEntity<User> actualResponse = userController.updateUser(1L,user);
        assertEquals(ResponseEntity.ok(updatedUser),actualResponse);
    }

    @Test
    void updateUserNotFound() {
        User user = getUser();
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        ResponseEntity<User> actualResponse = userController.updateUser(1L,user);
        assertEquals(ResponseEntity.notFound().build(),actualResponse);
    }

    @Test
    void deleteUserById() {
        doNothing().when(userRepository).deleteById(anyLong());

        ResponseEntity<String> actualResponse = userController.deleteUserById(1L);
        assertEquals(ResponseEntity.ok("User Deleted Successfully"),actualResponse);
    }

    private User getUser()
    {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        return user;
    }

    private List<User> getUserList()
    {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        users.add(user);
        user = new User();
        user.setFirstName("Jane");
        user.setLastName("Pickle");
        user.setEmail("janepickle@gmail.com");
        users.add(user);
        return users;
    }
}