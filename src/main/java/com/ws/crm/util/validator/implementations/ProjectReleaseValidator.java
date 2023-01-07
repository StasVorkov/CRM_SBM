package com.ws.crm.util.validator.implementations;

import com.ws.crm.models.Project;
import com.ws.crm.models.User;
import com.ws.crm.models.UserProject;
import com.ws.crm.services.ProjectService;
import com.ws.crm.services.UserService;
import com.ws.crm.util.validator.ProjectReleaseValidatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectReleaseValidator implements ProjectReleaseValidatable {

    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public ProjectReleaseValidator(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    public boolean validate(UserProject userProject) {
        Project project = getProject(userProject);
        User user = getUser(userProject);
        return user.getProjects().contains(project);
    }

    private Project getProject(UserProject userProject) {
        int projectId = userProject.getProject_id();
        return projectService.getProjectByID(projectId);
    }

    private User getUser(UserProject userProject) {
        int userId = userProject.getUser_id();
        return userService.getUserByID(userId);
    }
}
