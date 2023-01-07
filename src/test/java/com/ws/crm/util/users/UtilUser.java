package com.ws.crm.util.users;

import com.ws.crm.models.User;
import com.ws.crm.models.enums.Role;

import java.util.ArrayList;

public class UtilUser {
    private static final String NAME = "stubUser";
    private static final String SURNAME = "surname";
    private static final String PASSWORD = "passwords";
    private static final String EMAIL = "email@com";
    private static final String PHONE = "+48444555666";
    private static volatile User instance;
    private static final int id = 1;

    private UtilUser() {
    }

    public static User get() {
        if (instance == null) {
            synchronized (UtilUser.class) {
                if (instance == null) {
                    instance = createStub();
                }
            }
        }return instance;
    }

    private static User createStub() {
        User stubUser = new User();
        stubUser.setId(id);
        stubUser.setName(NAME);
        stubUser.setSurname(SURNAME);
        stubUser.setPassword(PASSWORD);
        stubUser.setEmail(EMAIL);
        stubUser.setProjects(new ArrayList<>());
        stubUser.setRole(Role.ROLE_MANAGER);
        stubUser.setOrders(new ArrayList<>());
        stubUser.setPhone(PHONE);
        return stubUser;
    }
}
