package com.example.demo.controllers;

import com.example.demo.*;
import com.example.demo.model.persistence.*;
import com.example.demo.model.persistence.repositories.*;
import org.junit.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.*;

import java.math.*;
import java.util.*;

import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;

    private ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

    @Before
    public void setUp () {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void test_get_all_items () {
        ResponseEntity<List<Item>> items = itemController.getItems();
        Assertions.assertNotNull(items);
        Assertions.assertEquals(200, items.getStatusCodeValue());
    }

    @Test
    public void test_get_all_items_by_id () {
        Item itemMock = new Item();
        itemMock.setId(1l);
        itemMock.setPrice(new BigDecimal(9.0));
        when(itemRepository.findById(1l)).thenReturn(Optional.of(itemMock));

        ResponseEntity<Item> item = itemController.getItemById(1l);
        Assertions.assertNotNull(item);
        Assertions.assertEquals(200, item.getStatusCodeValue());
    }

    @Test
    public void test_get_all_items_by_name () {
        List<Item> itemMocks = new ArrayList<>();
        itemMocks.add(new Item());
        when(itemRepository.findByName("Iphone 13")).thenReturn(itemMocks);

        ResponseEntity<List<Item>> items = itemController.getItemsByName("Iphone 13");
        Assertions.assertNotNull(items);
        Assertions.assertEquals(200, items.getStatusCodeValue());
        Assertions.assertEquals(1, items.getBody().size());
    }
}
