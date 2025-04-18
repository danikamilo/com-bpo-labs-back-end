package com.back.bpo.labs.ticketing.platform.user.service.impl;

import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import com.back.bpo.labs.ticketing.platform.user.model.User;
import com.back.bpo.labs.ticketing.platform.user.repository.UserRepository;
import com.back.bpo.labs.ticketing.platform.user.service.IUserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private UserRepository userRepository;

    @Override
    public List<User> listAllUsers() {
        try {
            List<User> users = userRepository.listAll();
            LOGGER.info("Users fetched successfully: {}", users.size());
            return users;
        } catch (Exception e) {
            LOGGER.error("Error fetching users", e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    @Override
    public User createUser(User user) {
        try {
            userRepository.persist(user);
            LOGGER.info("User created successfully: {}", user);
            return user;
        } catch (Exception e) {
            LOGGER.error("Error creating user: {}", user, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    @Override
    public User findById(String id) {
        try {
            User user = userRepository.findById(new org.bson.types.ObjectId(id));
            LOGGER.info("User found: {}", user);
            return user;
        } catch (Exception e) {
            LOGGER.error("Error finding user by ID: {}", id, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }
}