package com.ws.crm.controllers;

import com.ws.crm.dto.ProjectDTO;
import com.ws.crm.dto.UserDTO;
import com.ws.crm.exceptions.ProjectNotAssignedException;
import com.ws.crm.models.Project;
import com.ws.crm.models.User;
import com.ws.crm.models.UserProject;
import com.ws.crm.services.UserService;
import com.ws.crm.services.implementations.ProjectServiceImpl;
import com.ws.crm.services.implementations.UserProjectService;
import com.ws.crm.util.converter.DTOConvertable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("user-project")
@Log4j
public class UserProjectController {

    private final UserProjectService userProjectService;
    private final ProjectServiceImpl projectService;
    private final UserService userService;
    private final DTOConvertable<ProjectDTO, Project, Boolean> projectDTOConverter;
    private final DTOConvertable<UserDTO, User, Boolean> userDTOConverter;

    @GetMapping("/assign")
    public String getFormToAssigningUserToProject(Model model, @ModelAttribute UserProject userProject) {
        addUsersAndProjectsToModel(model);
        return "userproject/new_user_project";
    }

    private void addUsersAndProjectsToModel(Model model) {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDTO> projectsDTO = projectDTOConverter.convertListToDTO(projects,false);
        model.addAttribute("projectsDTO", projectsDTO);
        List<User> users = userService.getAllUsers();
        List<UserDTO> usersDTO = userDTOConverter.convertListToDTO(users,false);
        model.addAttribute("usersDTO", usersDTO);
    }

    @PostMapping("/assign")
    public String assignUser(@ModelAttribute UserProject userProject) {
        userProjectService.save(userProject);
        return "redirect:/users";
    }

    @GetMapping("/release")
    public String getFormForReleasingProject(Model model, @ModelAttribute UserProject userProject) {
        addUsersAndProjectsToModel(model);
        return "userproject/release_user_project";
    }

    @PatchMapping("/release")
    public String releaseProject(@ModelAttribute UserProject userProject) {
        try {
            userProjectService.delete(userProject);
        } catch (ProjectNotAssignedException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
        }
        return "redirect:/users";
    }
}
