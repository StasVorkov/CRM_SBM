package com.ws.crm.services.implementations;

import com.ws.crm.exceptions.EmailNotUniqueException;
import com.ws.crm.exceptions.ResourceNotFoundException;
import com.ws.crm.models.User;
import com.ws.crm.models.enums.Role;
import com.ws.crm.repositories.UsersRepository;
import com.ws.crm.services.UserService;
import com.ws.crm.util.validator.UserValidatable;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidatable userValidator;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           @Lazy UserValidatable userValidator) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAllOrderById();
    }


    @Override
    public User getUserByID(int id) throws ServiceException {
        try {
            return usersRepository.findUserByIdCustom(id).orElseThrow(ResourceNotFoundException::new);
        } catch (ResourceNotFoundException e) {
            log.error("User not found");
            throw new ServiceException("Service Error. Receiving User by Id failed. User not found", e);
        }
    }

    @Override
    @Transactional
    public User save(User user) throws EmailNotUniqueException {

        if (isUserNew(user)) {
            if (userValidator.validateByEmail(user)) {
                user.setRole(Role.ROLE_MANAGER);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                log.error("Duplicated email");
                throw new EmailNotUniqueException("Incorrect email. User have not saved.");
            }
        }
        usersRepository.save(user);
        return user;
    }

    private boolean isUserNew(User user) {
        return user.getId() == null;
    }

    @Override
    public void deleteUser(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) throws ServiceException {
        try {
            return usersRepository.getUserByEmail(email).orElseThrow(ResourceNotFoundException::new);
        } catch (ResourceNotFoundException e) {
            log.error("User not found");
            throw new ServiceException("Service Error. Receiving User by email failed. User not found");
        }
    }

    @Override
    public Optional<User> checkUserByEmail(String email) {
        return usersRepository.getUserByEmail(email);
    }
}
