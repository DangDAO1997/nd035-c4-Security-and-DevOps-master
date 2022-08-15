package com.example.demo.controllers;

import com.example.demo.*;
import com.example.demo.model.persistence.*;
import com.example.demo.model.persistence.repositories.*;
import com.example.demo.model.requests.*;
import org.junit.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.springframework.http.*;

import java.math.*;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    @Before
    public void setUp () {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void test_add_to_card_successfully() {
        User userMock = new User();
        userMock.setId(1L);
        userMock.setUsername("dang_dao");
        userMock.setCart(new Cart());
        when(userRepository.findByUsername("dang_dao")).thenReturn(userMock);
        when(cartRepository.save(any())).thenReturn(new Cart());
        when(itemRepository.findById(any())).thenReturn(Optional.of(new Item()));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setUsername("dang_dao");

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void test_add_to_cart_with_user_not_found() {
        when(userRepository.findByUsername("dang_dao")).thenReturn(null);

        ResponseEntity<Cart> response = cartController.addTocart(new ModifyCartRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void test_add_to_cart_with_item_not_found() {
        User userMock = new User();
        userMock.setId(1L);
        userMock.setUsername("dang_dao");
        when(userRepository.findByUsername("dang_dao")).thenReturn(userMock);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setUsername("dang_dao");
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void test_remove_from_card_successfully() {
        User userMock = new User();
        userMock.setId(1L);
        userMock.setUsername("dang_dao");
        userMock.setCart(new Cart());
        when(userRepository.findByUsername("dang_dao")).thenReturn(userMock);
        when(cartRepository.save(any())).thenReturn(new Cart());
        when(itemRepository.findById(any())).thenReturn(Optional.of(new Item()));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setUsername("dang_dao");

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void test_remove_from_cart_with_user_not_found() {
        when(userRepository.findByUsername("dang_dao")).thenReturn(null);
        ResponseEntity<Cart> response = cartController.removeFromcart(new ModifyCartRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart_with_item_not_found() {
        User userMock = new User();
        userMock.setId(1L);
        userMock.setUsername("dang_dao");
        when(userRepository.findByUsername("dang_dao")).thenReturn(userMock);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setUsername("dang_dao");
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }
}
