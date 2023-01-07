package com.ws.crm.services;

import com.ws.crm.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User getUserByID(int id);

    User save(User user);

    void deleteUser(int id);

    User getUserByEmail(String email);

    Optional<User> checkUserByEmail(String email);
}
