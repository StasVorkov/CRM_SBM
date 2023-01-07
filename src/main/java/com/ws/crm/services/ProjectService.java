package com.ws.crm.services;

import com.ws.crm.models.Project;
import com.ws.crm.models.User;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects();

    Project getProjectByID(int id);

    Project save(Project project);

    void deleteProject(int id);

    List<Project> getAllProjectsByUser(User authUser);
}
