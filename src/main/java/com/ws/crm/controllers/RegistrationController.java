package com.ws.crm.controllers;

import com.ws.crm.exceptions.EmailNotUniqueException;
import com.ws.crm.models.User;
import com.ws.crm.services.UserService;
import com.ws.crm.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;


@Controller
@RequestMapping("/registration")
@Log4j
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String createRegistrationForm(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping()
    public String postRegistrationForm(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            userService.save(user);
        } catch (EmailNotUniqueException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return "redirect:/login";
    }
}
