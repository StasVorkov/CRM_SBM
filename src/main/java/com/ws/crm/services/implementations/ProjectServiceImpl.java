package com.ws.crm.services.implementations;

import com.ws.crm.exceptions.ResourceNotFoundException;
import com.ws.crm.models.Project;
import com.ws.crm.models.User;
import com.ws.crm.repositories.ProjectsRepository;


import lombok.extern.log4j.Log4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class ProjectServiceImpl implements com.ws.crm.services.ProjectService {

    private final ProjectsRepository projectsRepository;

    @Autowired
    public ProjectServiceImpl(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectsRepository.findAll();
    }

    @Override
    public Project getProjectByID(int id) throws ServiceException {
        try {
            return projectsRepository.findProjectByIdCustom(id).orElseThrow(ResourceNotFoundException::new);
        } catch (ResourceNotFoundException e) {
            log.error("Project not found");
            throw new ServiceException("Service Error. Receiving Project by Id failed. Project not found", e);
        }
    }

    @Override
    public Project save(Project project) {
        projectsRepository.save(project);
        return project;
    }

    @Override
    public void deleteProject(int id) {
        projectsRepository.deleteById(id);
    }

    @Override
    public List<Project> getAllProjectsByUser(User authUser) {
        return projectsRepository.findProjectsByUser(authUser.getId());
    }
}
