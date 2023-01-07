package com.ws.crm.controllers;

import com.ws.crm.dto.UserDTO;
import com.ws.crm.exceptions.EmailNotUniqueException;
import com.ws.crm.models.User;
import com.ws.crm.services.UserService;
import com.ws.crm.services.implementations.CrmUserDetails;
import com.ws.crm.util.converter.DTOConvertable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Log4j
public class UsersController {

    private final UserService userService;
    private final DTOConvertable<UserDTO, User, Boolean> userDTOConverter;

    @GetMapping()
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        List<UserDTO> usersDTO = userDTOConverter.convertListToDTO(users, false);
        model.addAttribute("usersDTO", usersDTO);
        return "/users/users";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        try {
            User user = userService.getUserByID(id);
            UserDTO userDTO = userDTOConverter.convertToDTO(user, true);
            model.addAttribute("userDTO", userDTO);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "/users/single_user";
    }

    @GetMapping("/{id}/edit")
    public String editUserById(@PathVariable int id, Model model) {
        try {
            User user = userService.getUserByID(id);
            UserDTO userDTO = userDTOConverter.convertToDTO(user, true);
            model.addAttribute("userDTO", userDTO);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "/users/edit_user";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("userDTO") @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/users/edit_user";
        }
        try {
            userService.save(userDTOConverter.convertToEntity(userDTO));
        } catch (EmailNotUniqueException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String getUserById(Model model, @AuthenticationPrincipal CrmUserDetails crmUserDetails) {
        try {
        String userEmail = crmUserDetails.getUsername();
        User user = userService.getUserByEmail(userEmail);
        UserDTO userDTO = userDTOConverter.convertToDTO(user, true);
        model.addAttribute("userDTO", userDTO);
        } catch (ServiceException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return "/users/single_user";
    }
}
