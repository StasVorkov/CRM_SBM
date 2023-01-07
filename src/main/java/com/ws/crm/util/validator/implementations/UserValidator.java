package com.ws.crm.util.validator.implementations;

import com.ws.crm.models.User;
import com.ws.crm.services.UserService;
import com.ws.crm.util.validator.UserValidatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserValidator implements UserValidatable {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean validateByEmail(User checkedUser) {
        Optional <User> user = userService.checkUserByEmail(checkedUser.getEmail());
        return user.isEmpty();
    }
}
