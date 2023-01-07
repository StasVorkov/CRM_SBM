package com.ws.crm.util.converter.implementations;

import com.ws.crm.dto.OrderDTO;
import com.ws.crm.dto.ProjectDTO;
import com.ws.crm.dto.UserDTO;
import com.ws.crm.models.Order;
import com.ws.crm.models.Project;
import com.ws.crm.models.User;
import com.ws.crm.services.UserService;
import com.ws.crm.util.converter.DTOConvertable;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDTOConverter implements DTOConvertable<UserDTO, User,Boolean> {

    private final ModelMapper modelMapper;
    private final UserService userService;

    private final DTOConvertable<ProjectDTO, Project,Boolean> projectDTOConverter;
    private final DTOConvertable<OrderDTO, Order,Boolean> orderDTOConverter;


    public UserDTOConverter(ModelMapper modelMapper, UserService userService,
                            @Lazy DTOConvertable<ProjectDTO, Project,Boolean> projectDTOConverter,
                            DTOConvertable<OrderDTO, Order,Boolean> orderDTOConverter) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.projectDTOConverter = projectDTOConverter;
        this.orderDTOConverter = orderDTOConverter;
    }

    @Override
    public List<UserDTO> convertListToDTO(List<User> users,Boolean deepMode) {
        return users.stream().map(user -> convertToDTO(user,deepMode)).toList();
    }


    @Override
    public UserDTO convertToDTO(User user, Boolean deepMode) {
        return deepMode ? deepConvert(user) : shallowConvert(user);
    }

    private UserDTO deepConvert(User user) {
        UserDTO userDTO = shallowConvert(user);
        userDTO.setProjects(projectDTOConverter.convertListToDTO(user.getProjects(),false));
        userDTO.setOrders(orderDTOConverter.convertListToDTO(user.getOrders(),false));
        return userDTO;
    }

    private UserDTO shallowConvert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    @Override
    public User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if (userDTO.getId() != null) {
            User oldUser = userService.getUserByID(userDTO.getId());
            user.setPassword(oldUser.getPassword());
        }
        return user;
    }
}
