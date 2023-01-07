package com.ws.crm.util.validator;

import com.ws.crm.models.User;

public interface UserValidatable {
    public boolean validateByEmail(User user);
}
