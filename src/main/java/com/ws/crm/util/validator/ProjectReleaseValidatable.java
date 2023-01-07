package com.ws.crm.util.validator;

import com.ws.crm.models.UserProject;

public interface ProjectReleaseValidatable {
    boolean validate(UserProject userProject);
}
