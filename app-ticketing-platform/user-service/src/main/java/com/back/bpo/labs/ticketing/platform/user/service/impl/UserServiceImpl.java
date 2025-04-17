package com.back.bpo.labs.ticketing.platform.user.service.impl;

import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import com.back.bpo.labs.ticketing.platform.user.model.User;
import com.back.bpo.labs.ticketing.platform.user.repository.UserRepository;
import com.back.bpo.labs.ticketing.platform.user.service.IUserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;


/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class UserServiceImpl implements IUserService {

    @Inject
    private UserRepository userRepository;

    public List<User> listAllUsers() {
        return userRepository.listAll();
    }

    public User createUser(User user) {
        try {
            userRepository.persist(user);
            return user;
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    public User findById(String id) {
        try {
            return userRepository.findById(new org.bson.types.ObjectId(id));
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }
}
