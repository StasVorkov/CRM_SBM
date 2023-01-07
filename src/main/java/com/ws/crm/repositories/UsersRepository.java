package com.ws.crm.repositories;

import com.ws.crm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {

    Optional<User> getUserByEmail(String email);

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id =?1")
    Optional<User> findUserByIdCustom(int id);
    @Query(value = "SELECT u FROM User u ORDER BY u.id")
    List<User> findAllOrderById();
    
}


