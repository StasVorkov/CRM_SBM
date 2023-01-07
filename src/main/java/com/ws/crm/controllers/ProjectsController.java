package com.ws.crm.controllers;

import com.ws.crm.dto.ProjectDTO;
import com.ws.crm.models.Project;
import com.ws.crm.services.implementations.ProjectServiceImpl;
import com.ws.crm.util.converter.DTOConvertable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
@Log4j
public class ProjectsController {

    private final ProjectServiceImpl projectService;
    private final DTOConvertable<ProjectDTO,Project, Boolean> projectDTOConverter;


    @GetMapping()
    public String getAllProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projectsDTO", projectDTOConverter.convertListToDTO(projects,false));
        return "/projects/projects";
    }

    @GetMapping("/{id}")
    public String getProjectById(@PathVariable int id, Model model) {
        try {
            Project project = projectService.getProjectByID(id);
            model.addAttribute("projectDTO", projectDTOConverter.convertToDTO(project,true));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return "/projects/single_project";
    }

    @GetMapping("/new")
    public String getFormForCreatingProject(@ModelAttribute("projectDTO") ProjectDTO projectDTO) {
        return "/projects/new_project";
    }

    @PostMapping("/new")
    public String createProject(@ModelAttribute("projectDTO") @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/projects/new_project";
        }
        projectService.save(projectDTOConverter.convertToEntity(projectDTO));
        return "redirect:/projects";
    }

    @GetMapping("/{id}/edit")
    public String editProjectById(@PathVariable int id, Model model) {
        try {
            Project project = projectService.getProjectByID(id);
            model.addAttribute("projectDTO", projectDTOConverter.convertToDTO(project,true));
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return "/projects/edit_project";
    }

    @PatchMapping("/{id}")
    public String updateProject(@ModelAttribute("projectDTO") @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/projects/edit_project";
        }
        projectService.save(projectDTOConverter.convertToEntity(projectDTO));
        return "redirect:/projects";
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}
