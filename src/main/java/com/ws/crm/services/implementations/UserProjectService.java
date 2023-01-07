package com.ws.crm.services.implementations;

import com.ws.crm.exceptions.ProjectNotAssignedException;
import com.ws.crm.models.UserProject;
import com.ws.crm.repositories.UserProjectRepository;
import com.ws.crm.util.validator.implementations.ProjectReleaseValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j
public class UserProjectService implements com.ws.crm.services.UserProjectService {
    private final UserProjectRepository userProjectRepository;

    private final ProjectReleaseValidator projectReleaseValidator;

    @Override
    public UserProject save(UserProject userProject) {
        userProjectRepository.save(userProject);
        return userProject;
    }

    @Transactional
    @Override
    public void delete(UserProject userProject) throws ProjectNotAssignedException {
        if (projectReleaseValidator.validate(userProject)) {
            userProjectRepository.delete(userProject);
        } else {
            log.error("Project is not assigned to user");
            throw new ProjectNotAssignedException("Project is not assigned to user");
        }
    }
}
