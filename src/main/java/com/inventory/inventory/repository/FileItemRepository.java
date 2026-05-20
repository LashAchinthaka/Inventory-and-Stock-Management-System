package com.inventory.inventory.repository;

import com.inventory.inventory.model.Item;

import java.util.List;

public interface FileItemRepository {

    void save(Item item);

    List<Item> findAll();

    void delete(int id);

    void update(Item item);
}