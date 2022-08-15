package com.example.demo.controllers;

import com.example.demo.*;
import com.example.demo.model.persistence.*;
import com.example.demo.model.persistence.repositories.*;
import com.example.demo.model.requests.*;
import org.junit.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.*;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
    }

    @Test
    public void test_create_user_successfully() {
        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("testHashedPassword");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("dang_dao");
        request.setPassword("testPassword");
        request.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        Assertions.assertNotNull(user);
        Assertions.assertEquals(0, user.getId());
        Assertions.assertEquals("dang_dao", user.getUsername());
        Assertions.assertEquals("testHashedPassword", user.getPassword());
    }

    @Test
    public void test_create_user_password_failed() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("dang_dao");
        request.setPassword("testPassword");
        request.setConfirmPassword("other_testPassword");
        ResponseEntity<?> response = userController.createUser(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void test_find_user_by_user_id() {
        User userMock = new User();
        userMock.setId(1L);
        when(userRepository.findById(1l)).thenReturn(Optional.of(userMock));
        ResponseEntity<User> user = userController.findById(1l);
        Assertions.assertEquals(200, user.getStatusCodeValue());
    }

    @Test
    public void test_find_user_by_username() {
        User userMock = new User();
        userMock.setId(1L);
        userMock.setUsername("dang_dao");
        when(userRepository.findByUsername("dang_dao")).thenReturn(userMock);
        ResponseEntity<User> user = userController.findByUserName("dang_dao");
        Assertions.assertEquals(200, user.getStatusCodeValue());
    }
}
