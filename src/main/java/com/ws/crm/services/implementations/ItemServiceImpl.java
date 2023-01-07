package com.ws.crm.services.implementations;

import com.ws.crm.exceptions.ResourceNotFoundException;
import com.ws.crm.models.Item;
import com.ws.crm.repositories.ItemsRepository;
import com.ws.crm.services.ItemService;
import lombok.extern.log4j.Log4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class ItemServiceImpl implements ItemService {

    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemServiceImpl(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public List<Item> findAllItems() {
        return itemsRepository.findItems();
    }

    @Override
    public Item getItemById(int id) throws ServiceException {
        try {
            return itemsRepository.findItemByIdCustom(id).orElseThrow(ResourceNotFoundException::new);
        } catch (ResourceNotFoundException e) {
            log.error("Item not found");
            throw new ServiceException("Service Error. Get Item failed. Item not found", e);
        }
    }

    @Override
    public Item saveItem(Item item) {
        itemsRepository.save(item);
        return item;
    }

    @Override
    public void deleteItem(int id) {
        itemsRepository.deleteById(id);
    }
}
