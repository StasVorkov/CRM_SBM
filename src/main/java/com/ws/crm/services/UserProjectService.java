package com.ws.crm.services;

import com.ws.crm.models.UserProject;

public interface UserProjectService {
    UserProject save(UserProject userProject);
    void delete(UserProject userProject);
}
