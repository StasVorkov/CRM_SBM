package com.ws.crm.services;

import com.ws.crm.models.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAllItems();

    Item getItemById(int id);

    Item saveItem(Item item);

    void deleteItem(int id);
}
