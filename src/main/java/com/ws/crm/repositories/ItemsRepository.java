package com.ws.crm.repositories;

import com.ws.crm.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemsRepository extends JpaRepository<Item,Integer> {

    @Query(value = "SELECT i FROM Item i LEFT JOIN FETCH i.order.user LEFT JOIN FETCH i.order.project ORDER BY i.id")
    List<Item> findItems();

    @Query(value = "SELECT i FROM Item i LEFT JOIN FETCH i.order WHERE i.id =?1")
    Optional<Item> findItemByIdCustom(int id);
}
