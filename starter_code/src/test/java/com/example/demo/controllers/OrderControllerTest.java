package com.example.demo.controllers;

import com.example.demo.*;
import com.example.demo.model.persistence.*;
import com.example.demo.model.persistence.repositories.*;
import org.junit.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.springframework.http.*;

import java.math.*;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;

    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp () {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void test_submit_oder_by_username_successfully () {
        User userMock = new User();
        userMock.setUsername("dang_dao");
        when(userRepository.findByUsername("dang_dao")).thenReturn(userMock);

        Optional<UserOrder> order = Optional.of(new UserOrder());
        when(orderRepository.findById(any())).thenReturn(order);

        // create item
        Item item = new Item();
        item.setId(1L);
        item.setPrice(new BigDecimal(9.0));
        List<Item> items = new ArrayList<>();
        items.add(item);

        // create cart
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(new BigDecimal(9.0));
        cart.setItems((items));

        // set cart for user order
        userMock.setCart(cart);
        when(userRepository.findByUsername(userMock.getUsername())).thenReturn(userMock);

        ResponseEntity<?> responseOder = orderController.submit("dang_dao");
        Assertions.assertNotNull(responseOder);
        Assertions.assertEquals(200, responseOder.getStatusCodeValue());
    }

    @Test
    public void test_submit_oder_by_username_failed () {
        User userMock = new User();
        userMock.setUsername("dang_dao");
        when(userRepository.findByUsername("dang_dao")).thenReturn(null);

        ResponseEntity<?> responseOder = orderController.submit("dang_dao");
        Assertions.assertNotNull(responseOder);
        Assertions.assertEquals(404, responseOder.getStatusCodeValue());
    }

    @Test
    public void test_get_oder_for_valid_user () {
        User userMock = new User();
        userMock.setUsername("dang_dao");
        when(userRepository.findByUsername("dang_dao")).thenReturn(userMock);

        Optional<UserOrder> userOrder = Optional.of(new UserOrder());
        when(orderRepository.findById(any())).thenReturn(userOrder);

        ResponseEntity<List<UserOrder>> responseOder = orderController.getOrdersForUser("dang_dao");
        Assertions.assertNotNull(responseOder);
        Assertions.assertEquals(200, responseOder.getStatusCodeValue());
    }

    @Test
    public void test_get_oder_by_invalid_usernam () {
        User userMock = new User();
        userMock.setUsername("dang_dao");
        when(userRepository.findByUsername("dang_dao")).thenReturn(null);

        ResponseEntity<List<UserOrder>> responseOder = orderController.getOrdersForUser("dang_dao");
        Assertions.assertNotNull(responseOder);
        Assertions.assertEquals(404, responseOder.getStatusCodeValue());
    }
}
