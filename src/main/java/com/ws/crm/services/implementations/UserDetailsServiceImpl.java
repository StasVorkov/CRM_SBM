package com.ws.crm.services.implementations;

import com.ws.crm.exceptions.ResourceNotFoundException;
import com.ws.crm.models.User;
import com.ws.crm.services.UserService;
import lombok.extern.log4j.Log4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Log4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws ServiceException{
        User user = userService.getUserByEmail(username);
        if (user == null) {
            log.error("User not found");
            throw new ResourceNotFoundException("User not found");
        }
        return new CrmUserDetails(user);
    }
}
