package com.ws.crm.util.converter.implementations;

import com.ws.crm.dto.OrderDTO;
import com.ws.crm.dto.ProjectDTO;
import com.ws.crm.dto.UserDTO;
import com.ws.crm.models.Order;
import com.ws.crm.models.Project;
import com.ws.crm.models.User;
import com.ws.crm.util.converter.DTOConvertable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectDTOConverter implements DTOConvertable<ProjectDTO, Project, Boolean> {

    private final ModelMapper modelMapper;
    private final DTOConvertable<OrderDTO,Order, Boolean> orderDTOConverter;
    private final DTOConvertable<UserDTO, User, Boolean> userDTOConverter;

    public ProjectDTOConverter(ModelMapper modelMapper,
                               DTOConvertable<OrderDTO, Order, Boolean> orderDTOConverter,
                               DTOConvertable<UserDTO, User,Boolean> userDTOConverter) {
        this.modelMapper = modelMapper;
        this.orderDTOConverter = orderDTOConverter;
        this.userDTOConverter = userDTOConverter;
    }

    @Override
    public List<ProjectDTO> convertListToDTO(List<Project> projects,Boolean deepMode) {
        return projects.stream().map(project -> convertToDTO(project,deepMode)).toList();
    }

    @Override
    public ProjectDTO convertToDTO(Project project, Boolean deepMode) {
        return deepMode ? deepConvert(project) : shallowConvert(project);
    }

    private ProjectDTO deepConvert(Project project) {
        ProjectDTO projectDTO = shallowConvert(project);
        projectDTO.setUsers(userDTOConverter.convertListToDTO(project.getUsers(),false));
        projectDTO.setOrders(orderDTOConverter.convertListToDTO(project.getOrders(),false));
        return projectDTO;
    }

    private ProjectDTO shallowConvert(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setAddress(project.getAddress());
        return projectDTO;
    }

    @Override
    public Project convertToEntity(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

}
